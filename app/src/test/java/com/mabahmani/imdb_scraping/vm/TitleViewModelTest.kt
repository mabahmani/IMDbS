package com.mabahmani.imdb_scraping.vm

import com.mabahmani.domain.interactor.*
import com.mabahmani.domain.vo.*
import com.mabahmani.domain.vo.common.Image
import com.mabahmani.domain.vo.common.TitleId
import com.mabahmani.imdb_scraping.ui.main.title.state.*
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

    @RelaxedMockK
    lateinit var getTitleFullCastsUseCase: GetTitleFullCastsUseCase

    @RelaxedMockK
    lateinit var getTitleParentsGuideUseCase: GetTitleParentsGuideUseCase

    @RelaxedMockK
    lateinit var getTitleTechnicalSpecsUseCase: GetTitleTechnicalSpecsUseCase

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


    @Test
    fun `test getTitleFullCastsUseCase initial state`() = runTest {
        val state = viewModel.titleFullCastsUiState.first()

        assert(state == TitleFullCastsUiState.Loading)
    }

    @Test
    fun `test getTitleFullCastsUseCase success`() = runTest {
        val titleFullCasts = mockk<TitleFullCasts>()

        every { titleFullCasts getProperty "name" } returns "titleName"
        every { titleFullCasts getProperty "year" } returns "titleYear"
        every { titleFullCasts getProperty "cover" } returns Image("coverImage")

        coEvery { getTitleFullCastsUseCase(any()) } returns Result.success(titleFullCasts)

        viewModel.launchGetTitleFullCastsUseCase(TitleId("tt000000"))

        val stateList = viewModel.titleFullCastsUiState.take(2).toList()

        assert(stateList[0] is TitleFullCastsUiState.Loading)
        assert(stateList[1] is TitleFullCastsUiState.ShowTitleFullCasts)
        assert((stateList[1] as TitleFullCastsUiState.ShowTitleFullCasts).titleFullCasts.name == "titleName")
        assert((stateList[1] as TitleFullCastsUiState.ShowTitleFullCasts).titleFullCasts.year == "titleYear")
        assert((stateList[1] as TitleFullCastsUiState.ShowTitleFullCasts).titleFullCasts.cover.value == "coverImage")


        coVerify { getTitleFullCastsUseCase(TitleId("tt000000")) }
    }

    @Test
    fun `test getTitleFullCastsUseCase network failure`() = runTest {
        coEvery { getTitleFullCastsUseCase(any()) } returns Result.failure(UnknownHostException())

        viewModel.launchGetTitleFullCastsUseCase(TitleId("tt000000"))

        val stateList = viewModel.titleFullCastsUiState.take(2).toList()

        assert(stateList[0] is TitleFullCastsUiState.Loading)
        assert(stateList[1] is TitleFullCastsUiState.NetworkError)

        coVerify { getTitleFullCastsUseCase(TitleId("tt000000")) }
    }

    @Test
    fun `test getTitleFullCastsUseCase failure`() = runTest {

        coEvery { getTitleFullCastsUseCase(any()) } returns Result.failure(RuntimeException())

        viewModel.launchGetTitleFullCastsUseCase(TitleId("tt000000"))

        val stateList = viewModel.titleFullCastsUiState.take(2).toList()

        assert(stateList[0] is TitleFullCastsUiState.Loading)
        assert(stateList[1] is TitleFullCastsUiState.Error)

        coVerify { getTitleFullCastsUseCase(TitleId("tt000000")) }
    }

    @Test
    fun `test getTitleParentsGuideUseCase initial state`() = runTest {
        val state = viewModel.titleParentsGuideUiState.first()

        assert(state == TitleParentsGuideUiState.Loading)
    }

    @Test
    fun `test getTitleParentsGuideUseCase success`() = runTest {
        val titleParentsGuide = mockk<TitleParentsGuide>()

        every { titleParentsGuide getProperty "name" } returns "titleName"
        every { titleParentsGuide getProperty "year" } returns "titleYear"
        every { titleParentsGuide getProperty "cover" } returns Image("coverImage")

        coEvery { getTitleParentsGuideUseCase(any()) } returns Result.success(titleParentsGuide)

        viewModel.launchGetTitleParentsGuideUseCase(TitleId("tt000000"))

        val stateList = viewModel.titleParentsGuideUiState.take(2).toList()

        assert(stateList[0] is TitleParentsGuideUiState.Loading)
        assert(stateList[1] is TitleParentsGuideUiState.ShowTitleParentsGuide)
        assert((stateList[1] as TitleParentsGuideUiState.ShowTitleParentsGuide).titleParentsGuide.name == "titleName")
        assert((stateList[1] as TitleParentsGuideUiState.ShowTitleParentsGuide).titleParentsGuide.year == "titleYear")
        assert((stateList[1] as TitleParentsGuideUiState.ShowTitleParentsGuide).titleParentsGuide.cover.value == "coverImage")


        coVerify { getTitleParentsGuideUseCase(TitleId("tt000000")) }
    }

    @Test
    fun `test getTitleParentsGuideUseCase network failure`() = runTest {
        coEvery { getTitleParentsGuideUseCase(any()) } returns Result.failure(UnknownHostException())

        viewModel.launchGetTitleParentsGuideUseCase(TitleId("tt000000"))

        val stateList = viewModel.titleParentsGuideUiState.take(2).toList()

        assert(stateList[0] is TitleParentsGuideUiState.Loading)
        assert(stateList[1] is TitleParentsGuideUiState.NetworkError)

        coVerify { getTitleParentsGuideUseCase(TitleId("tt000000")) }
    }

    @Test
    fun `test getTitleParentsGuideUseCase failure`() = runTest {

        coEvery { getTitleParentsGuideUseCase(any()) } returns Result.failure(RuntimeException())

        viewModel.launchGetTitleParentsGuideUseCase(TitleId("tt000000"))

        val stateList = viewModel.titleParentsGuideUiState.take(2).toList()

        assert(stateList[0] is TitleParentsGuideUiState.Loading)
        assert(stateList[1] is TitleParentsGuideUiState.Error)

        coVerify { getTitleParentsGuideUseCase(TitleId("tt000000")) }
    }


    @Test
    fun `test getTitleTechnicalSpecsUseCase initial state`() = runTest {
        val state = viewModel.titleTechnicalSpecsUiState.first()

        assert(state == TitleTechnicalSpecsUiState.Loading)
    }

    @Test
    fun `test getTitleTechnicalSpecsUseCase success`() = runTest {
        val titleTechnicalSpecs = mockk<TitleTechnicalSpecs>()

        every { titleTechnicalSpecs getProperty "name" } returns "titleName"
        every { titleTechnicalSpecs getProperty "year" } returns "titleYear"
        every { titleTechnicalSpecs getProperty "cover" } returns Image("coverImage")

        coEvery { getTitleTechnicalSpecsUseCase(any()) } returns Result.success(titleTechnicalSpecs)

        viewModel.launchGetTitleTechnicalSpecsUseCase(TitleId("tt000000"))

        val stateList = viewModel.titleTechnicalSpecsUiState.take(2).toList()

        assert(stateList[0] is TitleTechnicalSpecsUiState.Loading)
        assert(stateList[1] is TitleTechnicalSpecsUiState.ShowTitleTechnicalSpecs)
        assert((stateList[1] as TitleTechnicalSpecsUiState.ShowTitleTechnicalSpecs).titleTechnicalSpecs.name == "titleName")
        assert((stateList[1] as TitleTechnicalSpecsUiState.ShowTitleTechnicalSpecs).titleTechnicalSpecs.year == "titleYear")
        assert((stateList[1] as TitleTechnicalSpecsUiState.ShowTitleTechnicalSpecs).titleTechnicalSpecs.cover.value == "coverImage")


        coVerify { getTitleTechnicalSpecsUseCase(TitleId("tt000000")) }
    }

    @Test
    fun `test getTitleTechnicalSpecsUseCase network failure`() = runTest {
        coEvery { getTitleTechnicalSpecsUseCase(any()) } returns Result.failure(UnknownHostException())

        viewModel.launchGetTitleTechnicalSpecsUseCase(TitleId("tt000000"))

        val stateList = viewModel.titleTechnicalSpecsUiState.take(2).toList()

        assert(stateList[0] is TitleTechnicalSpecsUiState.Loading)
        assert(stateList[1] is TitleTechnicalSpecsUiState.NetworkError)

        coVerify { getTitleTechnicalSpecsUseCase(TitleId("tt000000")) }
    }

    @Test
    fun `test getTitleTechnicalSpecsUseCase failure`() = runTest {

        coEvery { getTitleTechnicalSpecsUseCase(any()) } returns Result.failure(RuntimeException())

        viewModel.launchGetTitleTechnicalSpecsUseCase(TitleId("tt000000"))

        val stateList = viewModel.titleTechnicalSpecsUiState.take(2).toList()

        assert(stateList[0] is TitleTechnicalSpecsUiState.Loading)
        assert(stateList[1] is TitleTechnicalSpecsUiState.Error)

        coVerify { getTitleTechnicalSpecsUseCase(TitleId("tt000000")) }
    }
}