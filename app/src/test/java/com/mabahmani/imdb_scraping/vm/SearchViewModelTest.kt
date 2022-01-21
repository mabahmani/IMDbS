package com.mabahmani.imdb_scraping.vm

import com.mabahmani.domain.interactor.GetGenresUseCase
import com.mabahmani.domain.vo.common.Genre
import com.mabahmani.domain.vo.common.Image
import com.mabahmani.imdb_scraping.ui.main.home.state.HomeUiState
import com.mabahmani.imdb_scraping.ui.main.search.state.GenresUiState
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.*
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import java.lang.RuntimeException
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
class SearchViewModelTest {

    @RelaxedMockK
    lateinit var getGenresUseCase: GetGenresUseCase



    @InjectMockKs
    lateinit var viewModel: SearchViewModel

    private lateinit var coroutineDispatcher: TestDispatcher

    @Before
    fun setUp() {
        coroutineDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(coroutineDispatcher)
        MockKAnnotations.init(this)
    }

    @Test
    fun `test getGenres initial state`() = runTest{
        val state = viewModel.genresUiState.first()

        assert(state == GenresUiState.Loading)
    }

    @Test
    fun `test getGenres success`() = runTest{
        val genres = listOf(mockk<Genre>())

        every { genres } returns listOf(Genre("genreName", Image("genreImage")))

        coEvery { getGenresUseCase() } returns Result.success(genres)

        viewModel.launchGetGenresUseCase()

        val stateList = viewModel.genresUiState.take(2).toList()

        assert(stateList[0] is GenresUiState.Loading)
        assert(stateList[1] is GenresUiState.ShowSearchData)
        assert((stateList[1] as GenresUiState.ShowSearchData).genres[0].name == "genreName")
        assert((stateList[1] as GenresUiState.ShowSearchData).genres[0].image.value == "genreImage")

        coVerify { getGenresUseCase() }
    }

    @Test
    fun `test getGenres network failure`() = runTest{

        coEvery { getGenresUseCase() } returns Result.failure(UnknownHostException())

        viewModel.launchGetGenresUseCase()

        val stateList = viewModel.genresUiState.take(2).toList()

        assert(stateList[0] is GenresUiState.Loading)
        assert(stateList[1] is GenresUiState.NetworkError)

        coVerify { getGenresUseCase() }
    }

    @Test
    fun `test getGenres failure`() = runTest{

        coEvery { getGenresUseCase() } returns Result.failure(RuntimeException("error message"))

        viewModel.launchGetGenresUseCase()

        val stateList = viewModel.genresUiState.take(2).toList()

        assert(stateList[0] is GenresUiState.Loading)
        assert(stateList[1] is GenresUiState.Error)
        assert((stateList[1] as GenresUiState.Error).message == "error message")

        coVerify { getGenresUseCase() }
    }


    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }
}