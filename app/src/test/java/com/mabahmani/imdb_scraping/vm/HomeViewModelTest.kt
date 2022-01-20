package com.mabahmani.imdb_scraping.vm

import com.mabahmani.domain.interactor.GetHomeExtraUseCase
import com.mabahmani.domain.interactor.GetHomeUseCase
import com.mabahmani.domain.vo.Home
import com.mabahmani.domain.vo.HomeExtra
import com.mabahmani.domain.vo.common.*
import com.mabahmani.domain.vo.enum.HomeMediaType
import com.mabahmani.imdb_scraping.ui.main.home.state.HomeExtraUiState
import com.mabahmani.imdb_scraping.ui.main.home.state.HomeUiState
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.lang.RuntimeException
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @RelaxedMockK
    lateinit var getHomeUseCase: GetHomeUseCase

    @RelaxedMockK
    lateinit var getHomeExtraUseCase: GetHomeExtraUseCase

    @InjectMockKs
    private lateinit var viewModel: HomeViewModel

    private lateinit var coroutineDispatcher: TestDispatcher


    @Before
    fun setUp() {
        coroutineDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(coroutineDispatcher)
        MockKAnnotations.init(this)
    }

    @Test
    fun `test home initial state`() = runTest{
        val state = viewModel.homeUiState.first()

        assert(state == HomeUiState.Loading)
    }

    @Test
    fun `test homeExtra initial state`() = runTest{
        val state = viewModel.homeExtraUiState.first()

        assert(state == HomeExtraUiState.Loading)
    }

    @Test
    fun `test get home success`() = runTest{
        val home = mockk<Home>()

        every { home getProperty "trailers" } returns listOf( Trailer("trailerTitle", "trailerCaption", TitleId("tt000000"), VideoId("vi000000"), "4:30", Image("videoPreview"), Image("titleCover")))
        every { home getProperty "featuredToday" } returns listOf(Home.Media("mediaTitle", "mediaCaption", HomeMediaType.LIST, Image("image"), "ls000000"))

        coEvery { getHomeUseCase() } returns Result.success(home)

        viewModel.launchHomeUseCase()

        val stateList = viewModel.homeUiState.take(2).toList()

        assert(stateList[0] is HomeUiState.Loading)
        assert(stateList[1] is HomeUiState.ShowHomeData)
        assert((stateList[1] as HomeUiState.ShowHomeData).home.trailers[0].title == "trailerTitle")
        assert((stateList[1] as HomeUiState.ShowHomeData).home.featuredToday[0].title == "mediaTitle")

        coVerify { getHomeUseCase() }
    }

    @Test
    fun `test get homeExtra success`() = runTest{
        val homeExtra = mockk<HomeExtra>()

        every { homeExtra getProperty "fanPicksTitles" } returns listOf( Title(null, null, null, null, null, null, "movieTitle", "1995", null, null, null , null, null, null, TitleId("tt000000"),null, null, null, null))
        every { homeExtra getProperty "bornTodayList" } returns listOf(HomeExtra.BornToday(Image("avatar"), "celebName", "1990", "", "32", NameId("nm000000")))

        coEvery { getHomeExtraUseCase() } returns Result.success(homeExtra)

        viewModel.launchHomeExtraUseCase()

        val stateList = viewModel.homeExtraUiState.take(2).toList()

        assert(stateList[0] is HomeExtraUiState.Loading)
        assert(stateList[1] is HomeExtraUiState.ShowHomeExtraData)
        assert((stateList[1] as HomeExtraUiState.ShowHomeExtraData).homeExtra.fanPicksTitles[0].title == "movieTitle")
        assert((stateList[1] as HomeExtraUiState.ShowHomeExtraData).homeExtra.bornTodayList[0].name == "celebName")

        coVerify { getHomeExtraUseCase() }
    }

    @Test
    fun `test get home network failure`() = runTest{

        coEvery { getHomeUseCase() } returns Result.failure(UnknownHostException())

        viewModel.launchHomeUseCase()

        val stateList = viewModel.homeUiState.take(2).toList()

        assert(stateList[0] is HomeUiState.Loading)
        assert(stateList[1] is HomeUiState.NetworkError)

        coVerify { getHomeUseCase() }
    }

    @Test
    fun `test get homeExtra network failure`() = runTest{

        coEvery { getHomeExtraUseCase() } returns Result.failure(UnknownHostException())

        viewModel.launchHomeUseCase()

        val stateList = viewModel.homeExtraUiState.take(2).toList()

        assert(stateList[0] is HomeExtraUiState.Loading)
        assert(stateList[1] is HomeExtraUiState.NetworkError)

        coVerify { getHomeExtraUseCase() }
    }

    @Test
    fun `test get homeExtra failure`() = runTest{

        coEvery { getHomeExtraUseCase() } returns Result.failure(RuntimeException())

        viewModel.launchHomeUseCase()

        val stateList = viewModel.homeExtraUiState.take(2).toList()

        assert(stateList[0] is HomeExtraUiState.Loading)
        assert(stateList[1] is HomeExtraUiState.Error)

        coVerify { getHomeExtraUseCase() }
    }

    @Test
    fun `test get home failure`() = runTest{

        coEvery { getHomeUseCase() } returns Result.failure(RuntimeException())

        viewModel.launchHomeUseCase()

        val stateList = viewModel.homeUiState.take(2).toList()

        assert(stateList[0] is HomeUiState.Loading)
        assert(stateList[1] is HomeUiState.Error)

        coVerify { getHomeUseCase() }
    }

    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }
}