package com.mabahmani.imdb_scraping.vm

import com.mabahmani.domain.interactor.GetNewsDetailsUseCase
import com.mabahmani.domain.vo.NewsDetails
import com.mabahmani.domain.vo.common.Image
import com.mabahmani.domain.vo.common.NewsId
import com.mabahmani.imdb_scraping.ui.main.news.state.NewsDetailsUiState
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
class NewsViewModelTest {

    @RelaxedMockK
    lateinit var getNewsDetailsUseCase: GetNewsDetailsUseCase

    @InjectMockKs
    private lateinit var viewModel: NewsViewModel

    private lateinit var coroutineDispatcher: TestDispatcher

    @Before
    fun setUp() {
        coroutineDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(coroutineDispatcher)
        MockKAnnotations.init(this)
    }


    @Test
    fun `test get newsDetailsUseCase initial state`() = runTest{
        val state = viewModel.newsDetailsUiState.first()

        assert(state == NewsDetailsUiState.Loading)
    }

    @Test
    fun `test get newsDetailsUseCase success`() = runTest{
        val newsDetails = mockk<NewsDetails>()

        every { newsDetails getProperty "title" } returns "title"
        every { newsDetails getProperty "date" } returns "date"
        every { newsDetails getProperty "author" } returns "author"
        every { newsDetails getProperty "newsAgency" } returns "newsAgency"
        every { newsDetails getProperty "image" } returns Image("image")
        every { newsDetails getProperty "content" } returns "content"
        every { newsDetails getProperty "link" } returns "link"

        coEvery { getNewsDetailsUseCase(any()) } returns Result.success(newsDetails)

        viewModel.launchGetNewsDetailsUseCase(NewsId("ni000000"))

        val stateList = viewModel.newsDetailsUiState.take(2).toList()

        assert(stateList[0] is NewsDetailsUiState.Loading)
        assert(stateList[1] is NewsDetailsUiState.ShowNewsDetails)
        assert((stateList[1] as NewsDetailsUiState.ShowNewsDetails).newsDetails.title == "title")
        assert((stateList[1] as NewsDetailsUiState.ShowNewsDetails).newsDetails.date == "date")
        assert((stateList[1] as NewsDetailsUiState.ShowNewsDetails).newsDetails.author == "author")
        assert((stateList[1] as NewsDetailsUiState.ShowNewsDetails).newsDetails.newsAgency == "newsAgency")
        assert((stateList[1] as NewsDetailsUiState.ShowNewsDetails).newsDetails.content == "content")
        assert((stateList[1] as NewsDetailsUiState.ShowNewsDetails).newsDetails.link == "link")
        assert((stateList[1] as NewsDetailsUiState.ShowNewsDetails).newsDetails.image.value == "image")

        coVerify { getNewsDetailsUseCase(NewsId("ni000000")) }
    }

    @Test
    fun `test get newsDetailsUseCase network failure`() = runTest{

        coEvery { getNewsDetailsUseCase(any()) } returns Result.failure(UnknownHostException())

        viewModel.launchGetNewsDetailsUseCase(NewsId("ni000000"))

        val stateList = viewModel.newsDetailsUiState.take(2).toList()

        assert(stateList[0] is NewsDetailsUiState.Loading)
        assert(stateList[1] is NewsDetailsUiState.NetworkError)

        coVerify { getNewsDetailsUseCase(NewsId("ni000000")) }
    }

    @Test
    fun `test get newsDetailsUseCase failure`() = runTest{

        coEvery { getNewsDetailsUseCase(any()) } returns Result.failure(RuntimeException())

        viewModel.launchGetNewsDetailsUseCase(NewsId("ni000000"))

        val stateList = viewModel.newsDetailsUiState.take(2).toList()

        assert(stateList[0] is NewsDetailsUiState.Loading)
        assert(stateList[1] is NewsDetailsUiState.Error)

        coVerify { getNewsDetailsUseCase(NewsId("ni000000")) }
    }


    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }
}