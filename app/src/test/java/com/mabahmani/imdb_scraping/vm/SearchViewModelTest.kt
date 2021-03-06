package com.mabahmani.imdb_scraping.vm

import com.mabahmani.domain.interactor.*
import com.mabahmani.domain.vo.Calender
import com.mabahmani.domain.vo.Suggestion
import com.mabahmani.domain.vo.common.*
import com.mabahmani.imdb_scraping.ui.main.search.state.*
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


    @Test
    fun `test getKeywords initial state`() = runTest {
        val state = viewModel.keywordsUiState.first()

        assert(state == KeywordsUiState.Loading)
    }

    @Test
    fun `test getKeywords success`() = runTest {

        val keyword = "keywordName"

        coEvery { getKeywordsUseCase() } returns Result.success(listOf(keyword))

        viewModel.launchGetKeywordsUseCase()

        val stateList = viewModel.keywordsUiState.take(2).toList()

        assert(stateList[0] is KeywordsUiState.Loading)
        assert(stateList[1] is KeywordsUiState.ShowSearchData)
        assert((stateList[1] as KeywordsUiState.ShowSearchData).keywords[0] == "keywordName")

        coVerify { getKeywordsUseCase() }
    }

    @Test
    fun `test getKeywords network failure`() = runTest {

        coEvery { getKeywordsUseCase() } returns Result.failure(UnknownHostException())

        viewModel.launchGetKeywordsUseCase()

        val stateList = viewModel.keywordsUiState.take(2).toList()

        assert(stateList[0] is KeywordsUiState.Loading)
        assert(stateList[1] is KeywordsUiState.NetworkError)

        coVerify { getKeywordsUseCase() }
    }

    @Test
    fun `test getKeywords failure`() = runTest {

        coEvery { getKeywordsUseCase() } returns Result.failure(RuntimeException("error message"))

        viewModel.launchGetKeywordsUseCase()

        val stateList = viewModel.keywordsUiState.take(2).toList()

        assert(stateList[0] is KeywordsUiState.Loading)
        assert(stateList[1] is KeywordsUiState.Error)
        assert((stateList[1] as KeywordsUiState.Error).message == "error message")

        coVerify { getKeywordsUseCase() }
    }

    @Test
    fun `test getEvents initial state`() = runTest {
        val state = viewModel.eventsUiState.first()

        assert(state == EventsUiState.Loading)
    }

    @Test
    fun `test getEvents success`() = runTest {

        val event = mockk<Event>()

        every { event getProperty "name" } returns "eventName"
        every { event getProperty "eventId" } returns EventId("ev000000")

        coEvery { getEventsUseCase() } returns Result.success(listOf(event))

        viewModel.launchGetEventsUseCase()

        val stateList = viewModel.eventsUiState.take(2).toList()

        assert(stateList[0] is EventsUiState.Loading)
        assert(stateList[1] is EventsUiState.ShowSearchData)
        assert((stateList[1] as EventsUiState.ShowSearchData).events[0].name == "eventName")
        assert((stateList[1] as EventsUiState.ShowSearchData).events[0].eventId.value == "ev000000")

        coVerify { getEventsUseCase() }
    }

    @Test
    fun `test getEvents network failure`() = runTest {

        coEvery { getEventsUseCase() } returns Result.failure(UnknownHostException())

        viewModel.launchGetEventsUseCase()

        val stateList = viewModel.eventsUiState.take(2).toList()

        assert(stateList[0] is EventsUiState.Loading)
        assert(stateList[1] is EventsUiState.NetworkError)

        coVerify { getEventsUseCase() }
    }

    @Test
    fun `test getEvents failure`() = runTest {

        coEvery { getEventsUseCase() } returns Result.failure(RuntimeException("error message"))

        viewModel.launchGetEventsUseCase()

        val stateList = viewModel.eventsUiState.take(2).toList()

        assert(stateList[0] is EventsUiState.Loading)
        assert(stateList[1] is EventsUiState.Error)
        assert((stateList[1] as EventsUiState.Error).message == "error message")

        coVerify { getEventsUseCase() }
    }

    @Test
    fun `test getCalenders initial state`() = runTest {
        val state = viewModel.calenderUiState.first()

        assert(state == CalenderUiState.Loading)
    }

    @Test
    fun `test getCalenders success`() = runTest {

        val calender = mockk<Calender>()
        val calenderTitle = mockk<TitleLink>()

        every { calender getProperty "date" } returns "01 December 2021"
        every { calender getProperty "titles" } returns listOf(calenderTitle)
        every { calenderTitle getProperty "title" } returns "movieName"
        every { calenderTitle getProperty "titleId" } returns TitleId("tt000000")

        coEvery { getCalenderUseCase() } returns Result.success(listOf(calender))

        viewModel.launchGetCalenderUseCase()

        val stateList = viewModel.calenderUiState.take(2).toList()

        assert(stateList[0] is CalenderUiState.Loading)
        assert(stateList[1] is CalenderUiState.ShowSearchData)
        assert((stateList[1] as CalenderUiState.ShowSearchData).calenders[0].date == "01 December 2021")
        assert((stateList[1] as CalenderUiState.ShowSearchData).calenders[0].titles[0].title == "movieName")
        assert((stateList[1] as CalenderUiState.ShowSearchData).calenders[0].titles[0].titleId.value == "tt000000")

        coVerify { getCalenderUseCase() }
    }

    @Test
    fun `test getCalenders network failure`() = runTest {

        coEvery { getCalenderUseCase() } returns Result.failure(UnknownHostException())

        viewModel.launchGetCalenderUseCase()

        val stateList = viewModel.calenderUiState.take(2).toList()

        assert(stateList[0] is CalenderUiState.Loading)
        assert(stateList[1] is CalenderUiState.NetworkError)

        coVerify { getCalenderUseCase() }
    }

    @Test
    fun `test getCalenders failure`() = runTest {

        coEvery { getCalenderUseCase() } returns Result.failure(RuntimeException("error message"))

        viewModel.launchGetCalenderUseCase()

        val stateList = viewModel.calenderUiState.take(2).toList()

        assert(stateList[0] is CalenderUiState.Loading)
        assert(stateList[1] is CalenderUiState.Error)
        assert((stateList[1] as CalenderUiState.Error).message == "error message")

        coVerify { getCalenderUseCase() }
    }

    @Test
    fun `test suggest initial state`() = runTest {
        val state = viewModel.suggestionsUiState.first()

        assert(state == SuggestionsUiState.Idle)
    }

    @Test
    fun `test suggest success`() = runTest {

        val suggestion = mockk<Suggestion>()

        every { suggestion getProperty "name" } returns "suggest name"

        coEvery { suggestUseCase(any()) } returns Result.success(listOf(suggestion))

        viewModel.launchSuggestUseCase("harry")

        val stateList = viewModel.suggestionsUiState.take(2).toList()

        assert(stateList[0] is SuggestionsUiState.Loading)
        assert(stateList[1] is SuggestionsUiState.ShowSearchData)
        assert((stateList[1] as SuggestionsUiState.ShowSearchData).suggestions[0].name == "suggest name")

        coVerify { suggestUseCase("harry") }
    }

    @Test
    fun `test suggest network failure`() = runTest {

        coEvery { suggestUseCase(any()) } returns Result.failure(UnknownHostException())

        viewModel.launchSuggestUseCase("harry")

        val stateList = viewModel.suggestionsUiState.take(2).toList()

        assert(stateList[0] is SuggestionsUiState.Loading)
        assert(stateList[1] is SuggestionsUiState.NetworkError)

        coVerify { suggestUseCase("harry") }
    }

    @Test
    fun `test suggest failure`() = runTest {

        coEvery { suggestUseCase(any()) } returns Result.failure(RuntimeException("error message"))

        viewModel.launchSuggestUseCase("harry")

        val stateList = viewModel.suggestionsUiState.take(2).toList()

        assert(stateList[0] is SuggestionsUiState.Loading)
        assert(stateList[1] is SuggestionsUiState.Error)
        assert((stateList[1] as SuggestionsUiState.Error).message == "error message")

        coVerify { suggestUseCase("harry") }
    }

    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }
}