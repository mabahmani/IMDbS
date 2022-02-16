package com.mabahmani.imdb_scraping.vm

import com.mabahmani.domain.interactor.GetTitleAwardsUseCase
import com.mabahmani.domain.interactor.GetTitleDetailsUseCase
import com.mabahmani.domain.vo.TitleAwards
import com.mabahmani.domain.vo.TitleDetails
import com.mabahmani.domain.vo.common.Image
import com.mabahmani.domain.vo.common.TitleId
import com.mabahmani.imdb_scraping.ui.main.title.state.TitleAwardsUiState
import com.mabahmani.imdb_scraping.ui.main.title.state.TitleDetailUiState
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
class TitleViewModelTest {

    @RelaxedMockK
    lateinit var getTitleDetailsUseCase: GetTitleDetailsUseCase

    @RelaxedMockK
    lateinit var getTitleAwardsUseCase: GetTitleAwardsUseCase

    @InjectMockKs
    private lateinit var viewModel: TitleViewModel

    private lateinit var coroutineDispatcher: TestDispatcher

    @Before
    fun setUp() {
        coroutineDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(coroutineDispatcher)
        MockKAnnotations.init(this)
    }

    @Test
    fun `test titleDetails initial state`() = runTest {
        val state = viewModel.titleDetailsUiState.first()

        assert(state == TitleDetailUiState.Loading)
    }

    @Test
    fun `test get titleDetails success`() = runTest {
        val titleDetails = mockk<TitleDetails>()

        every { titleDetails getProperty "name" } returns "titleName"
        every { titleDetails getProperty "releaseYear" } returns "releaseYear"
        every { titleDetails getProperty "cover" } returns Image("coverImage")
        every { titleDetails getProperty "plot" } returns "plot"

        coEvery { getTitleDetailsUseCase(any()) } returns Result.success(titleDetails)

        viewModel.launchGetTitleDetailsUseCase(TitleId("tt000000"))

        val stateList = viewModel.titleDetailsUiState.take(2).toList()

        assert(stateList[0] is TitleDetailUiState.Loading)
        assert(stateList[1] is TitleDetailUiState.ShowTitleDetails)
        assert((stateList[1] as TitleDetailUiState.ShowTitleDetails).titleDetails.name == "titleName")
        assert((stateList[1] as TitleDetailUiState.ShowTitleDetails).titleDetails.releaseYear == "releaseYear")
        assert((stateList[1] as TitleDetailUiState.ShowTitleDetails).titleDetails.cover.value == "coverImage")
        assert((stateList[1] as TitleDetailUiState.ShowTitleDetails).titleDetails.plot == "plot")


        coVerify { getTitleDetailsUseCase(TitleId("tt000000")) }
    }

    @Test
    fun `test get titleDetails network failure`() = runTest {
        coEvery { getTitleDetailsUseCase(any()) } returns Result.failure(UnknownHostException())

        viewModel.launchGetTitleDetailsUseCase(TitleId("tt000000"))

        val stateList = viewModel.titleDetailsUiState.take(2).toList()

        assert(stateList[0] is TitleDetailUiState.Loading)
        assert(stateList[1] is TitleDetailUiState.NetworkError)

        coVerify { getTitleDetailsUseCase(TitleId("tt000000")) }
    }

    @Test
    fun `test get titleDetails failure`() = runTest {

        coEvery { getTitleDetailsUseCase(any()) } returns Result.failure(RuntimeException())

        viewModel.launchGetTitleDetailsUseCase(TitleId("tt000000"))

        val stateList = viewModel.titleDetailsUiState.take(2).toList()

        assert(stateList[0] is TitleDetailUiState.Loading)
        assert(stateList[1] is TitleDetailUiState.Error)

        coVerify { getTitleDetailsUseCase(TitleId("tt000000")) }
    }

    @Test
    fun `test getTitleAwardsUseCase initial state`() = runTest {
        val state = viewModel.titleAwardsUiState.first()

        assert(state == TitleAwardsUiState.Loading)
    }

    @Test
    fun `test getTitleAwardsUseCase success`() = runTest {
        val titleAwards = mockk<TitleAwards>()

        every { titleAwards getProperty "name" } returns "titleName"
        every { titleAwards getProperty "cover" } returns Image("coverImage")

        coEvery { getTitleAwardsUseCase(any()) } returns Result.success(titleAwards)

        viewModel.launchGetTitleAwardsUseCase(TitleId("tt000000"))

        val stateList = viewModel.titleAwardsUiState.take(2).toList()

        assert(stateList[0] is TitleAwardsUiState.Loading)
        assert(stateList[1] is TitleAwardsUiState.ShowTitleAwards)
        assert((stateList[1] as TitleAwardsUiState.ShowTitleAwards).titleAwards.name == "titleName")
        assert((stateList[1] as TitleAwardsUiState.ShowTitleAwards).titleAwards.cover.value == "coverImage")


        coVerify { getTitleAwardsUseCase(TitleId("tt000000")) }
    }

    @Test
    fun `test getTitleAwardsUseCase network failure`() = runTest {
        coEvery { getTitleAwardsUseCase(any()) } returns Result.failure(UnknownHostException())

        viewModel.launchGetTitleAwardsUseCase(TitleId("tt000000"))

        val stateList = viewModel.titleAwardsUiState.take(2).toList()

        assert(stateList[0] is TitleAwardsUiState.Loading)
        assert(stateList[1] is TitleAwardsUiState.NetworkError)

        coVerify { getTitleAwardsUseCase(TitleId("tt000000")) }
    }

    @Test
    fun `test getTitleAwardsUseCase failure`() = runTest {

        coEvery { getTitleAwardsUseCase(any()) } returns Result.failure(RuntimeException())

        viewModel.launchGetTitleAwardsUseCase(TitleId("tt000000"))

        val stateList = viewModel.titleAwardsUiState.take(2).toList()

        assert(stateList[0] is TitleAwardsUiState.Loading)
        assert(stateList[1] is TitleAwardsUiState.Error)

        coVerify { getTitleAwardsUseCase(TitleId("tt000000")) }
    }
}