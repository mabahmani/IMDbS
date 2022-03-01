package com.mabahmani.imdb_scraping.vm

import com.mabahmani.domain.interactor.*
import com.mabahmani.domain.vo.common.BoxOffice
import com.mabahmani.domain.vo.common.Image
import com.mabahmani.domain.vo.common.Title
import com.mabahmani.domain.vo.common.TitleId
import com.mabahmani.imdb_scraping.ui.main.charts.state.ChartBoxOfficeUiState
import com.mabahmani.imdb_scraping.ui.main.charts.state.ChartUiState
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
class ChartViewModelTest {

    @RelaxedMockK
    lateinit var getBottom100MoviesUseCase: GetBottom100MoviesUseCase

    @RelaxedMockK
    lateinit var getBoxOfficeUseCase: GetBoxOfficeUseCase

    @RelaxedMockK
    lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase

    @RelaxedMockK
    lateinit var getPopularTvShowsUseCase: GetPopularTvShowsUseCase

    @RelaxedMockK
    lateinit var getTop250MoviesUseCase: GetTop250MoviesUseCase

    @RelaxedMockK
    lateinit var getTop250TvShowsUseCase: GetTop250TvShowsUseCase

    @InjectMockKs
    private lateinit var viewModel: ChartViewModel

    private lateinit var coroutineDispatcher: TestDispatcher

    @Before
    fun setUp() {
        coroutineDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(coroutineDispatcher)
        MockKAnnotations.init(this)
    }

    @Test
    fun `test getBottom100MoviesUseCase initial state`() = runTest {
        val state = viewModel.chartsUiState.first()

        assert(state == ChartUiState.Loading)
    }

    @Test
    fun `test getBottom100MoviesUseCase success`() = runTest {
        val title = mockk<Title>()

        every { title getProperty "title" } returns "titleName"
        every { title getProperty "cover" } returns Image("titleImage")
        every { title getProperty "releaseYear" } returns "1995"
        every { title getProperty "rate" } returns "9.55"
        every { title getProperty "titleId" } returns TitleId("tt000000")

        coEvery { getBottom100MoviesUseCase() } returns Result.success(listOf(title))

        viewModel.launchGetBottom100MoviesUseCase()

        val stateList = viewModel.chartsUiState.take(2).toList()

        assert(stateList[0] is ChartUiState.Loading)
        assert(stateList[1] is ChartUiState.ShowChartsData)
        assert((stateList[1] as ChartUiState.ShowChartsData).titles[0].title == "titleName")
        assert((stateList[1] as ChartUiState.ShowChartsData).titles[0].releaseYear == "1995")
        assert((stateList[1] as ChartUiState.ShowChartsData).titles[0].rate == "9.55")
        assert((stateList[1] as ChartUiState.ShowChartsData).titles[0].cover?.value == "titleImage")
        assert((stateList[1] as ChartUiState.ShowChartsData).titles[0].titleId?.value == "tt000000")

        coVerify { getBottom100MoviesUseCase() }
    }

    @Test
    fun `test getBottom100MoviesUseCase network failure`() = runTest {

        coEvery { getBottom100MoviesUseCase() } returns Result.failure(UnknownHostException())

        viewModel.launchGetBottom100MoviesUseCase()

        val stateList = viewModel.chartsUiState.take(2).toList()

        assert(stateList[0] is ChartUiState.Loading)
        assert(stateList[1] is ChartUiState.NetworkError)

        coVerify { getBottom100MoviesUseCase() }
    }

    @Test
    fun `test getBottom100MoviesUseCase failure`() = runTest {

        coEvery { getBottom100MoviesUseCase() } returns Result.failure(RuntimeException("error message"))

        viewModel.launchGetBottom100MoviesUseCase()

        val stateList = viewModel.chartsUiState.take(2).toList()

        assert(stateList[0] is ChartUiState.Loading)
        assert(stateList[1] is ChartUiState.Error)
        assert((stateList[1] as ChartUiState.Error).message == "error message")

        coVerify { getBottom100MoviesUseCase() }
    }


    @Test
    fun `test getPopularMoviesUseCase initial state`() = runTest {
        val state = viewModel.chartsUiState.first()

        assert(state == ChartUiState.Loading)
    }

    @Test
    fun `test getPopularMoviesUseCase success`() = runTest {
        val title = mockk<Title>()

        every { title getProperty "title" } returns "titleName"
        every { title getProperty "cover" } returns Image("titleImage")
        every { title getProperty "releaseYear" } returns "1995"
        every { title getProperty "rate" } returns "9.55"
        every { title getProperty "titleId" } returns TitleId("tt000000")

        coEvery { getPopularMoviesUseCase() } returns Result.success(listOf(title))

        viewModel.launchGetPopularMoviesUseCase()

        val stateList = viewModel.chartsUiState.take(2).toList()

        assert(stateList[0] is ChartUiState.Loading)
        assert(stateList[1] is ChartUiState.ShowChartsData)
        assert((stateList[1] as ChartUiState.ShowChartsData).titles[0].title == "titleName")
        assert((stateList[1] as ChartUiState.ShowChartsData).titles[0].releaseYear == "1995")
        assert((stateList[1] as ChartUiState.ShowChartsData).titles[0].rate == "9.55")
        assert((stateList[1] as ChartUiState.ShowChartsData).titles[0].cover?.value == "titleImage")
        assert((stateList[1] as ChartUiState.ShowChartsData).titles[0].titleId?.value == "tt000000")

        coVerify { getPopularMoviesUseCase() }
    }

    @Test
    fun `test getPopularMoviesUseCase network failure`() = runTest {

        coEvery { getPopularMoviesUseCase() } returns Result.failure(UnknownHostException())

        viewModel.launchGetPopularMoviesUseCase()

        val stateList = viewModel.chartsUiState.take(2).toList()

        assert(stateList[0] is ChartUiState.Loading)
        assert(stateList[1] is ChartUiState.NetworkError)

        coVerify { getPopularMoviesUseCase() }
    }

    @Test
    fun `test getPopularMoviesUseCase failure`() = runTest {

        coEvery { getPopularMoviesUseCase() } returns Result.failure(RuntimeException("error message"))

        viewModel.launchGetPopularMoviesUseCase()

        val stateList = viewModel.chartsUiState.take(2).toList()

        assert(stateList[0] is ChartUiState.Loading)
        assert(stateList[1] is ChartUiState.Error)
        assert((stateList[1] as ChartUiState.Error).message == "error message")

        coVerify { getPopularMoviesUseCase() }
    }

    @Test
    fun `test getPopularTvShowsUseCase initial state`() = runTest {
        val state = viewModel.chartsUiState.first()

        assert(state == ChartUiState.Loading)
    }

    @Test
    fun `test getPopularTvShowsUseCase success`() = runTest {
        val title = mockk<Title>()

        every { title getProperty "title" } returns "titleName"
        every { title getProperty "cover" } returns Image("titleImage")
        every { title getProperty "releaseYear" } returns "1995"
        every { title getProperty "rate" } returns "9.55"
        every { title getProperty "titleId" } returns TitleId("tt000000")

        coEvery { getPopularTvShowsUseCase() } returns Result.success(listOf(title))

        viewModel.launchGetPopularTvShowsUseCase()

        val stateList = viewModel.chartsUiState.take(2).toList()

        assert(stateList[0] is ChartUiState.Loading)
        assert(stateList[1] is ChartUiState.ShowChartsData)
        assert((stateList[1] as ChartUiState.ShowChartsData).titles[0].title == "titleName")
        assert((stateList[1] as ChartUiState.ShowChartsData).titles[0].releaseYear == "1995")
        assert((stateList[1] as ChartUiState.ShowChartsData).titles[0].rate == "9.55")
        assert((stateList[1] as ChartUiState.ShowChartsData).titles[0].cover?.value == "titleImage")
        assert((stateList[1] as ChartUiState.ShowChartsData).titles[0].titleId?.value == "tt000000")

        coVerify { getPopularTvShowsUseCase() }
    }

    @Test
    fun `test getPopularTvShowsUseCase network failure`() = runTest {

        coEvery { getPopularTvShowsUseCase() } returns Result.failure(UnknownHostException())

        viewModel.launchGetPopularTvShowsUseCase()

        val stateList = viewModel.chartsUiState.take(2).toList()

        assert(stateList[0] is ChartUiState.Loading)
        assert(stateList[1] is ChartUiState.NetworkError)

        coVerify { getPopularTvShowsUseCase() }
    }

    @Test
    fun `test getPopularTvShowsUseCase failure`() = runTest {

        coEvery { getPopularTvShowsUseCase() } returns Result.failure(RuntimeException("error message"))

        viewModel.launchGetPopularTvShowsUseCase()

        val stateList = viewModel.chartsUiState.take(2).toList()

        assert(stateList[0] is ChartUiState.Loading)
        assert(stateList[1] is ChartUiState.Error)
        assert((stateList[1] as ChartUiState.Error).message == "error message")

        coVerify { getPopularTvShowsUseCase() }
    }

    @Test
    fun `test getTop250MoviesUseCase initial state`() = runTest {
        val state = viewModel.chartsUiState.first()

        assert(state == ChartUiState.Loading)
    }

    @Test
    fun `test getTop250MoviesUseCase success`() = runTest {
        val title = mockk<Title>()

        every { title getProperty "title" } returns "titleName"
        every { title getProperty "cover" } returns Image("titleImage")
        every { title getProperty "releaseYear" } returns "1995"
        every { title getProperty "rate" } returns "9.55"
        every { title getProperty "titleId" } returns TitleId("tt000000")

        coEvery { getTop250MoviesUseCase() } returns Result.success(listOf(title))

        viewModel.launchGetTop250MoviesUseCase()

        val stateList = viewModel.chartsUiState.take(2).toList()

        assert(stateList[0] is ChartUiState.Loading)
        assert(stateList[1] is ChartUiState.ShowChartsData)
        assert((stateList[1] as ChartUiState.ShowChartsData).titles[0].title == "titleName")
        assert((stateList[1] as ChartUiState.ShowChartsData).titles[0].releaseYear == "1995")
        assert((stateList[1] as ChartUiState.ShowChartsData).titles[0].rate == "9.55")
        assert((stateList[1] as ChartUiState.ShowChartsData).titles[0].cover?.value == "titleImage")
        assert((stateList[1] as ChartUiState.ShowChartsData).titles[0].titleId?.value == "tt000000")

        coVerify { getTop250MoviesUseCase() }
    }

    @Test
    fun `test getTop250MoviesUseCase network failure`() = runTest {

        coEvery { getTop250MoviesUseCase() } returns Result.failure(UnknownHostException())

        viewModel.launchGetTop250MoviesUseCase()

        val stateList = viewModel.chartsUiState.take(2).toList()

        assert(stateList[0] is ChartUiState.Loading)
        assert(stateList[1] is ChartUiState.NetworkError)

        coVerify { getTop250MoviesUseCase() }
    }

    @Test
    fun `test getTop250MoviesUseCase failure`() = runTest {

        coEvery { getTop250MoviesUseCase() } returns Result.failure(RuntimeException("error message"))

        viewModel.launchGetTop250MoviesUseCase()

        val stateList = viewModel.chartsUiState.take(2).toList()

        assert(stateList[0] is ChartUiState.Loading)
        assert(stateList[1] is ChartUiState.Error)
        assert((stateList[1] as ChartUiState.Error).message == "error message")

        coVerify { getTop250MoviesUseCase() }
    }


    @Test
    fun `test getTop250TvShowsUseCase initial state`() = runTest {
        val state = viewModel.chartsUiState.first()

        assert(state == ChartUiState.Loading)
    }

    @Test
    fun `test getTop250TvShowsUseCase success`() = runTest {
        val title = mockk<Title>()

        every { title getProperty "title" } returns "titleName"
        every { title getProperty "cover" } returns Image("titleImage")
        every { title getProperty "releaseYear" } returns "1995"
        every { title getProperty "rate" } returns "9.55"
        every { title getProperty "titleId" } returns TitleId("tt000000")

        coEvery { getTop250TvShowsUseCase() } returns Result.success(listOf(title))

        viewModel.launchGetTop250TvShowsUseCase()

        val stateList = viewModel.chartsUiState.take(2).toList()

        assert(stateList[0] is ChartUiState.Loading)
        assert(stateList[1] is ChartUiState.ShowChartsData)
        assert((stateList[1] as ChartUiState.ShowChartsData).titles[0].title == "titleName")
        assert((stateList[1] as ChartUiState.ShowChartsData).titles[0].releaseYear == "1995")
        assert((stateList[1] as ChartUiState.ShowChartsData).titles[0].rate == "9.55")
        assert((stateList[1] as ChartUiState.ShowChartsData).titles[0].cover?.value == "titleImage")
        assert((stateList[1] as ChartUiState.ShowChartsData).titles[0].titleId?.value == "tt000000")

        coVerify { getTop250TvShowsUseCase() }
    }

    @Test
    fun `test getTop250TvShowsUseCase network failure`() = runTest {

        coEvery { getTop250TvShowsUseCase() } returns Result.failure(UnknownHostException())

        viewModel.launchGetTop250TvShowsUseCase()

        val stateList = viewModel.chartsUiState.take(2).toList()

        assert(stateList[0] is ChartUiState.Loading)
        assert(stateList[1] is ChartUiState.NetworkError)

        coVerify { getTop250TvShowsUseCase() }
    }

    @Test
    fun `test getTop250TvShowsUseCase failure`() = runTest {

        coEvery { getTop250TvShowsUseCase() } returns Result.failure(RuntimeException("error message"))

        viewModel.launchGetTop250TvShowsUseCase()

        val stateList = viewModel.chartsUiState.take(2).toList()

        assert(stateList[0] is ChartUiState.Loading)
        assert(stateList[1] is ChartUiState.Error)
        assert((stateList[1] as ChartUiState.Error).message == "error message")

        coVerify { getTop250TvShowsUseCase() }
    }

    @Test
    fun `test getBoxOfficeUseCase initial state`() = runTest {
        val state = viewModel.boxOfficeUiState.first()

        assert(state == ChartBoxOfficeUiState.Loading)
    }

    @Test
    fun `test getBoxOfficeUseCase success`() = runTest {
        val boxOffice = mockk<BoxOffice>()
        val boxOfficeItem = mockk<BoxOffice.BoxOfficeItem>()

        every { boxOfficeItem getProperty "weekendGross" } returns "titleWeekendGross"
        every { boxOfficeItem getProperty "gross" } returns "titleGross"
        every { boxOfficeItem getProperty "weeks" } returns "numberOfWeeks"
        every { boxOfficeItem getProperty "title" } returns "titleName"
        every { boxOfficeItem getProperty "titleId" } returns TitleId("tt000000")
        every { boxOfficeItem getProperty "image" } returns Image("titleImage")

        every { boxOffice getProperty "startDate" } returns "date"
        every { boxOffice getProperty "endDate" } returns "date"
        every { boxOffice getProperty "boxOfficeItems" } returns listOf(boxOfficeItem)


        coEvery { getBoxOfficeUseCase() } returns Result.success(boxOffice)

        viewModel.launchGetBoxOfficeUseCase()

        val stateList = viewModel.boxOfficeUiState.take(2).toList()

        assert(stateList[0] is ChartBoxOfficeUiState.Loading)
        assert(stateList[1] is ChartBoxOfficeUiState.ShowBoxOfficeData)
        assert((stateList[1] as ChartBoxOfficeUiState.ShowBoxOfficeData).boxOffice.startDate == "date")
        assert((stateList[1] as ChartBoxOfficeUiState.ShowBoxOfficeData).boxOffice.endDate == "date")
        assert((stateList[1] as ChartBoxOfficeUiState.ShowBoxOfficeData).boxOffice.boxOfficeItems?.get(0)?.gross == "titleGross")
        assert((stateList[1] as ChartBoxOfficeUiState.ShowBoxOfficeData).boxOffice.boxOfficeItems?.get(0)?.weekendGross == "titleWeekendGross")
        assert((stateList[1] as ChartBoxOfficeUiState.ShowBoxOfficeData).boxOffice.boxOfficeItems?.get(0)?.weeks == "numberOfWeeks")
        assert((stateList[1] as ChartBoxOfficeUiState.ShowBoxOfficeData).boxOffice.boxOfficeItems?.get(0)?.title == "titleName")
        assert((stateList[1] as ChartBoxOfficeUiState.ShowBoxOfficeData).boxOffice.boxOfficeItems?.get(0)?.titleId?.value == "tt000000")
        assert((stateList[1] as ChartBoxOfficeUiState.ShowBoxOfficeData).boxOffice.boxOfficeItems?.get(0)?.image?.value == "titleImage")


        coVerify { getBoxOfficeUseCase() }
    }

    @Test
    fun `test getBoxOfficeUseCase network failure`() = runTest {

        coEvery { getBoxOfficeUseCase() } returns Result.failure(UnknownHostException())

        viewModel.launchGetBoxOfficeUseCase()

        val stateList = viewModel.boxOfficeUiState.take(2).toList()

        assert(stateList[0] is ChartBoxOfficeUiState.Loading)
        assert(stateList[1] is ChartBoxOfficeUiState.NetworkError)

        coVerify { getBoxOfficeUseCase() }
    }

    @Test
    fun `test getBoxOfficeUseCase failure`() = runTest {

        coEvery { getBoxOfficeUseCase() } returns Result.failure(RuntimeException("error message"))

        viewModel.launchGetBoxOfficeUseCase()

        val stateList = viewModel.boxOfficeUiState.take(2).toList()

        assert(stateList[0] is ChartBoxOfficeUiState.Loading)
        assert(stateList[1] is ChartBoxOfficeUiState.Error)
        assert((stateList[1] as ChartBoxOfficeUiState.Error).message == "error message")

        coVerify { getBoxOfficeUseCase() }
    }

    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }
}