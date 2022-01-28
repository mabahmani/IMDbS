package com.mabahmani.imdb_scraping.vm

import com.mabahmani.domain.interactor.*
import com.mabahmani.domain.vo.common.Genre
import com.mabahmani.domain.vo.common.Image
import com.mabahmani.imdb_scraping.ui.main.search.state.CelebsUiState
import com.mabahmani.imdb_scraping.ui.main.search.state.GenresUiState
import com.mabahmani.imdb_scraping.ui.main.search.state.TitlesUiState
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import java.lang.RuntimeException
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
class SearchViewModelTest {

    @RelaxedMockK
    lateinit var advancedNameSearchUseCase: AdvancedNameSearchUseCase

    @RelaxedMockK
    lateinit var advancedTitleSearchUseCase: AdvancedTitleSearchUseCase

    @RelaxedMockK
    lateinit var getCalenderUseCase: GetCalenderUseCase

    @RelaxedMockK
    lateinit var getCelebsUseCase: GetCelebsUseCase

    @RelaxedMockK
    lateinit var getTitlesUseCase: GetTitlesUseCase

    @RelaxedMockK
    lateinit var getEventsUseCase: GetEventsUseCase

    @RelaxedMockK
    lateinit var getGenresUseCase: GetGenresUseCase

    @RelaxedMockK
    lateinit var getKeywordsUseCase: GetKeywordsUseCase

    @RelaxedMockK
    lateinit var searchTitlesByGenreUseCase: SearchTitlesByGenreUseCase

    @RelaxedMockK
    lateinit var searchTitlesByKeywordsUseCase: SearchTitlesByKeywordsUseCase

    @RelaxedMockK
    lateinit var suggestCelebUseCase: SuggestCelebUseCase

    @RelaxedMockK
    lateinit var suggestTitleUseCase: SuggestTitleUseCase

    @RelaxedMockK
    lateinit var suggestUseCase: SuggestUseCase

    @InjectMockKs
    private lateinit var viewModel: SearchViewModel

    private lateinit var coroutineDispatcher: TestDispatcher

    @Before
    fun setUp() {
        coroutineDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(coroutineDispatcher)
        MockKAnnotations.init(this)
    }

    @Test
    fun `test getGenres initial state`() = runTest {
        val state = viewModel.genresUiState.first()

        assert(state == GenresUiState.Loading)
    }

    @Test
    fun `test getGenres success`() = runTest {
        val genre = mockk<Genre>()

        every { genre getProperty "name" } returns "genreName"
        every { genre getProperty "image" } returns Image("genreImage")

        coEvery { getGenresUseCase() } returns Result.success(listOf(genre))

        viewModel.launchGetGenresUseCase()

        val stateList = viewModel.genresUiState.take(2).toList()

        assert(stateList[0] is GenresUiState.Loading)
        assert(stateList[1] is GenresUiState.ShowSearchData)
        assert((stateList[1] as GenresUiState.ShowSearchData).genres[0].name == "genreName")
        assert((stateList[1] as GenresUiState.ShowSearchData).genres[0].image.value == "genreImage")

        coVerify { getGenresUseCase() }
    }

    @Test
    fun `test getGenres network failure`() = runTest {

        coEvery { getGenresUseCase() } returns Result.failure(UnknownHostException())

        viewModel.launchGetGenresUseCase()

        val stateList = viewModel.genresUiState.take(2).toList()

        assert(stateList[0] is GenresUiState.Loading)
        assert(stateList[1] is GenresUiState.NetworkError)

        coVerify { getGenresUseCase() }
    }

    @Test
    fun `test getGenres failure`() = runTest {

        coEvery { getGenresUseCase() } returns Result.failure(RuntimeException("error message"))

        viewModel.launchGetGenresUseCase()

        val stateList = viewModel.genresUiState.take(2).toList()

        assert(stateList[0] is GenresUiState.Loading)
        assert(stateList[1] is GenresUiState.Error)
        assert((stateList[1] as GenresUiState.Error).message == "error message")

        coVerify { getGenresUseCase() }
    }

    @Test
    fun `test getCelebs initial state`() = runTest {
        val state = viewModel.celebsUiState.first()

        assert(state == CelebsUiState.Loading)
    }

    @Test
    fun `test getCelebs success`() = runTest {

        viewModel.launchGetCelebsUseCase()

        val stateList = viewModel.celebsUiState.take(2).toList()

        assert(stateList[0] is CelebsUiState.Loading)
        assert(stateList[1] is CelebsUiState.ShowSearchData)

        coVerify { getCelebsUseCase() }
    }

    @Test
    fun `test getTitles initial state`() = runTest {
        val state = viewModel.titlesUiState.first()

        assert(state == TitlesUiState.Loading)
    }

    @Test
    fun `test getTitles success`() = runTest {

        viewModel.launchGetTitlesUseCase()

        val stateList = viewModel.titlesUiState.take(2).toList()

        assert(stateList[0] is TitlesUiState.Loading)
        assert(stateList[1] is TitlesUiState.ShowSearchData)

        coVerify { getTitlesUseCase() }
    }

    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }
}