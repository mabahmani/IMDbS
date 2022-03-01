package com.mabahmani.imdb_scraping.vm

import com.mabahmani.domain.interactor.GetAnticipatedTrailersUseCase
import com.mabahmani.domain.interactor.GetPopularTrailersUseCase
import com.mabahmani.domain.interactor.GetRecentTrailersUseCase
import com.mabahmani.domain.interactor.GetTrendingTrailersUseCase
import com.mabahmani.domain.vo.common.Image
import com.mabahmani.domain.vo.common.TitleId
import com.mabahmani.domain.vo.common.Trailer
import com.mabahmani.domain.vo.common.VideoId
import com.mabahmani.imdb_scraping.ui.main.trailers.state.TrailersUiState
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
class TrailerViewModelTest {

    @RelaxedMockK
    lateinit var getAnticipatedTrailersUseCase: GetAnticipatedTrailersUseCase

    @RelaxedMockK
    lateinit var getTrendingTrailersUseCase: GetTrendingTrailersUseCase

    @RelaxedMockK
    lateinit var getRecentTrailersUseCase: GetRecentTrailersUseCase

    @RelaxedMockK
    lateinit var getPopularTrailersUseCase: GetPopularTrailersUseCase

    @InjectMockKs
    private lateinit var viewModel: TrailerViewModel

    private lateinit var coroutineDispatcher: TestDispatcher

    @Before
    fun setUp() {
        coroutineDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(coroutineDispatcher)
        MockKAnnotations.init(this)
    }

    @Test
    fun `test getAnticipatedTrailersUseCase initial state`() = runTest {
        val state = viewModel.trailersUiState.first()

        assert(state == TrailersUiState.Loading)
    }

    @Test
    fun `test getAnticipatedTrailersUseCase success`() = runTest {
        val trailer = mockk<Trailer>()

        every { trailer getProperty "title" } returns "titleName"
        every { trailer getProperty "titleCover" } returns Image("titleCover")
        every { trailer getProperty "videoId" } returns VideoId("vi000000")
        every { trailer getProperty "caption" } returns "videoCaption"
        every { trailer getProperty "titleId" } returns TitleId("tt000000")
        every { trailer getProperty "duration" } returns "1:45"

        coEvery { getAnticipatedTrailersUseCase() } returns Result.success(listOf(trailer))

        viewModel.launchGetAnticipatedTrailersUseCase()

        val stateList = viewModel.trailersUiState.take(2).toList()

        assert(stateList[0] is TrailersUiState.Loading)
        assert(stateList[1] is TrailersUiState.ShowTrailersData)
        assert((stateList[1] as TrailersUiState.ShowTrailersData).trailers[0].title == "titleName")
        assert((stateList[1] as TrailersUiState.ShowTrailersData).trailers[0].titleCover.value == "titleCover")
        assert((stateList[1] as TrailersUiState.ShowTrailersData).trailers[0].videoId.value == "vi000000")
        assert((stateList[1] as TrailersUiState.ShowTrailersData).trailers[0].caption == "videoCaption")
        assert((stateList[1] as TrailersUiState.ShowTrailersData).trailers[0].titleId.value == "tt000000")
        assert((stateList[1] as TrailersUiState.ShowTrailersData).trailers[0].duration == "1:45")

        coVerify { getAnticipatedTrailersUseCase() }
    }

    @Test
    fun `test getAnticipatedTrailersUseCase network failure`() = runTest {

        coEvery { getAnticipatedTrailersUseCase() } returns Result.failure(UnknownHostException())

        viewModel.launchGetAnticipatedTrailersUseCase()

        val stateList = viewModel.trailersUiState.take(2).toList()

        assert(stateList[0] is TrailersUiState.Loading)
        assert(stateList[1] is TrailersUiState.NetworkError)

        coVerify { getAnticipatedTrailersUseCase() }
    }

    @Test
    fun `test getAnticipatedTrailersUseCase failure`() = runTest {

        coEvery { getAnticipatedTrailersUseCase() } returns Result.failure(RuntimeException("error message"))

        viewModel.launchGetAnticipatedTrailersUseCase()

        val stateList = viewModel.trailersUiState.take(2).toList()

        assert(stateList[0] is TrailersUiState.Loading)
        assert(stateList[1] is TrailersUiState.Error)
        assert((stateList[1] as TrailersUiState.Error).message == "error message")

        coVerify { getAnticipatedTrailersUseCase() }
    }


    @Test
    fun `test getTrendingTrailersUseCase initial state`() = runTest {
        val state = viewModel.trailersUiState.first()

        assert(state == TrailersUiState.Loading)
    }

    @Test
    fun `test getTrendingTrailersUseCase success`() = runTest {
        val trailer = mockk<Trailer>()

        every { trailer getProperty "title" } returns "titleName"
        every { trailer getProperty "titleCover" } returns Image("titleCover")
        every { trailer getProperty "videoId" } returns VideoId("vi000000")
        every { trailer getProperty "caption" } returns "videoCaption"
        every { trailer getProperty "titleId" } returns TitleId("tt000000")
        every { trailer getProperty "duration" } returns "1:45"

        coEvery { getTrendingTrailersUseCase() } returns Result.success(listOf(trailer))

        viewModel.launchGetTrendingTrailersUseCase()

        val stateList = viewModel.trailersUiState.take(2).toList()

        assert(stateList[0] is TrailersUiState.Loading)
        assert(stateList[1] is TrailersUiState.ShowTrailersData)
        assert((stateList[1] as TrailersUiState.ShowTrailersData).trailers[0].title == "titleName")
        assert((stateList[1] as TrailersUiState.ShowTrailersData).trailers[0].titleCover.value == "titleCover")
        assert((stateList[1] as TrailersUiState.ShowTrailersData).trailers[0].videoId.value == "vi000000")
        assert((stateList[1] as TrailersUiState.ShowTrailersData).trailers[0].caption == "videoCaption")
        assert((stateList[1] as TrailersUiState.ShowTrailersData).trailers[0].titleId.value == "tt000000")
        assert((stateList[1] as TrailersUiState.ShowTrailersData).trailers[0].duration == "1:45")

        coVerify { getTrendingTrailersUseCase() }
    }

    @Test
    fun `test getTrendingTrailersUseCase network failure`() = runTest {

        coEvery { getTrendingTrailersUseCase() } returns Result.failure(UnknownHostException())

        viewModel.launchGetTrendingTrailersUseCase()

        val stateList = viewModel.trailersUiState.take(2).toList()

        assert(stateList[0] is TrailersUiState.Loading)
        assert(stateList[1] is TrailersUiState.NetworkError)

        coVerify { getTrendingTrailersUseCase() }
    }

    @Test
    fun `test getTrendingTrailersUseCase failure`() = runTest {

        coEvery { getTrendingTrailersUseCase() } returns Result.failure(RuntimeException("error message"))

        viewModel.launchGetTrendingTrailersUseCase()

        val stateList = viewModel.trailersUiState.take(2).toList()

        assert(stateList[0] is TrailersUiState.Loading)
        assert(stateList[1] is TrailersUiState.Error)
        assert((stateList[1] as TrailersUiState.Error).message == "error message")

        coVerify { getTrendingTrailersUseCase() }
    }

    @Test
    fun `test getRecentTrailersUseCase initial state`() = runTest {
        val state = viewModel.trailersUiState.first()

        assert(state == TrailersUiState.Loading)
    }

    @Test
    fun `test getRecentTrailersUseCase success`() = runTest {
        val trailer = mockk<Trailer>()

        every { trailer getProperty "title" } returns "titleName"
        every { trailer getProperty "titleCover" } returns Image("titleCover")
        every { trailer getProperty "videoId" } returns VideoId("vi000000")
        every { trailer getProperty "caption" } returns "videoCaption"
        every { trailer getProperty "titleId" } returns TitleId("tt000000")
        every { trailer getProperty "duration" } returns "1:45"

        coEvery { getRecentTrailersUseCase() } returns Result.success(listOf(trailer))

        viewModel.launchGetRecentTrailersUseCase()

        val stateList = viewModel.trailersUiState.take(2).toList()

        assert(stateList[0] is TrailersUiState.Loading)
        assert(stateList[1] is TrailersUiState.ShowTrailersData)
        assert((stateList[1] as TrailersUiState.ShowTrailersData).trailers[0].title == "titleName")
        assert((stateList[1] as TrailersUiState.ShowTrailersData).trailers[0].titleCover.value == "titleCover")
        assert((stateList[1] as TrailersUiState.ShowTrailersData).trailers[0].videoId.value == "vi000000")
        assert((stateList[1] as TrailersUiState.ShowTrailersData).trailers[0].caption == "videoCaption")
        assert((stateList[1] as TrailersUiState.ShowTrailersData).trailers[0].titleId.value == "tt000000")
        assert((stateList[1] as TrailersUiState.ShowTrailersData).trailers[0].duration == "1:45")

        coVerify { getRecentTrailersUseCase() }
    }

    @Test
    fun `test getRecentTrailersUseCase network failure`() = runTest {

        coEvery { getRecentTrailersUseCase() } returns Result.failure(UnknownHostException())

        viewModel.launchGetRecentTrailersUseCase()

        val stateList = viewModel.trailersUiState.take(2).toList()

        assert(stateList[0] is TrailersUiState.Loading)
        assert(stateList[1] is TrailersUiState.NetworkError)

        coVerify { getRecentTrailersUseCase() }
    }

    @Test
    fun `test getRecentTrailersUseCase failure`() = runTest {

        coEvery { getRecentTrailersUseCase() } returns Result.failure(RuntimeException("error message"))

        viewModel.launchGetRecentTrailersUseCase()

        val stateList = viewModel.trailersUiState.take(2).toList()

        assert(stateList[0] is TrailersUiState.Loading)
        assert(stateList[1] is TrailersUiState.Error)
        assert((stateList[1] as TrailersUiState.Error).message == "error message")

        coVerify { getRecentTrailersUseCase() }
    }

    @Test
    fun `test getPopularTrailersUseCase initial state`() = runTest {
        val state = viewModel.trailersUiState.first()

        assert(state == TrailersUiState.Loading)
    }

    @Test
    fun `test getPopularTrailersUseCase success`() = runTest {
        val trailer = mockk<Trailer>()

        every { trailer getProperty "title" } returns "titleName"
        every { trailer getProperty "titleCover" } returns Image("titleCover")
        every { trailer getProperty "videoId" } returns VideoId("vi000000")
        every { trailer getProperty "caption" } returns "videoCaption"
        every { trailer getProperty "titleId" } returns TitleId("tt000000")
        every { trailer getProperty "duration" } returns "1:45"

        coEvery { getPopularTrailersUseCase() } returns Result.success(listOf(trailer))

        viewModel.launchGetPopularTrailersUseCase()

        val stateList = viewModel.trailersUiState.take(2).toList()

        assert(stateList[0] is TrailersUiState.Loading)
        assert(stateList[1] is TrailersUiState.ShowTrailersData)
        assert((stateList[1] as TrailersUiState.ShowTrailersData).trailers[0].title == "titleName")
        assert((stateList[1] as TrailersUiState.ShowTrailersData).trailers[0].titleCover.value == "titleCover")
        assert((stateList[1] as TrailersUiState.ShowTrailersData).trailers[0].videoId.value == "vi000000")
        assert((stateList[1] as TrailersUiState.ShowTrailersData).trailers[0].caption == "videoCaption")
        assert((stateList[1] as TrailersUiState.ShowTrailersData).trailers[0].titleId.value == "tt000000")
        assert((stateList[1] as TrailersUiState.ShowTrailersData).trailers[0].duration == "1:45")

        coVerify { getPopularTrailersUseCase() }
    }

    @Test
    fun `test getPopularTrailersUseCase network failure`() = runTest {

        coEvery { getPopularTrailersUseCase() } returns Result.failure(UnknownHostException())

        viewModel.launchGetPopularTrailersUseCase()

        val stateList = viewModel.trailersUiState.take(2).toList()

        assert(stateList[0] is TrailersUiState.Loading)
        assert(stateList[1] is TrailersUiState.NetworkError)

        coVerify { getPopularTrailersUseCase() }
    }

    @Test
    fun `test getPopularTrailersUseCase failure`() = runTest {

        coEvery { getPopularTrailersUseCase() } returns Result.failure(RuntimeException("error message"))

        viewModel.launchGetPopularTrailersUseCase()

        val stateList = viewModel.trailersUiState.take(2).toList()

        assert(stateList[0] is TrailersUiState.Loading)
        assert(stateList[1] is TrailersUiState.Error)
        assert((stateList[1] as TrailersUiState.Error).message == "error message")

        coVerify { getPopularTrailersUseCase() }
    }

    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }
}