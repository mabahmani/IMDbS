package com.mabahmani.imdb_scraping.vm

import com.mabahmani.domain.interactor.GetNameAwardsUseCase
import com.mabahmani.domain.interactor.GetNameBioUseCase
import com.mabahmani.domain.interactor.GetNameDetailsUseCase
import com.mabahmani.domain.vo.NameAwards
import com.mabahmani.domain.vo.NameBio
import com.mabahmani.domain.vo.NameDetails
import com.mabahmani.domain.vo.common.Image
import com.mabahmani.domain.vo.common.NameId
import com.mabahmani.imdb_scraping.ui.main.name.state.NameAwardUiState
import com.mabahmani.imdb_scraping.ui.main.name.state.NameBioUiState
import com.mabahmani.imdb_scraping.ui.main.name.state.NameDetailUiState
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
class NameViewModelTest {

    @RelaxedMockK
    lateinit var getNameDetailsUseCase: GetNameDetailsUseCase

    @RelaxedMockK
    lateinit var getNameBioUseCase: GetNameBioUseCase

    @RelaxedMockK
    lateinit var getNameAwardsUseCase: GetNameAwardsUseCase

    @InjectMockKs
    private lateinit var viewModel: NameViewModel

    private lateinit var coroutineDispatcher: TestDispatcher


    @Before
    fun setUp() {
        coroutineDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(coroutineDispatcher)
        MockKAnnotations.init(this)
    }

    @Test
    fun `test nameDetails initial state`() = runTest{
        val state = viewModel.nameDetailsUiState.first()

        assert(state == NameDetailUiState.Loading)
    }

    @Test
    fun `test get nameDetails success`() = runTest{
        val nameDetails = mockk<NameDetails>()

        every { nameDetails getProperty "nameId" } returns NameId("nm000000")
        every { nameDetails getProperty "name" } returns "celebName"
        every { nameDetails getProperty "jobTitles" } returns listOf("actor")
        every { nameDetails getProperty "avatar" } returns Image("avatarImage")
        every { nameDetails getProperty "bioSummary" } returns "bioSummary"

        coEvery { getNameDetailsUseCase(any()) } returns Result.success(nameDetails)

        viewModel.launchGetNameDetailsUseCase(NameId("nm000000"))

        val stateList = viewModel.nameDetailsUiState.take(2).toList()

        assert(stateList[0] is NameDetailUiState.Loading)
        assert(stateList[1] is NameDetailUiState.ShowNameDetails)
        assert((stateList[1] as NameDetailUiState.ShowNameDetails).nameDetails.nameId.value == "nm000000")
        assert((stateList[1] as NameDetailUiState.ShowNameDetails).nameDetails.name == "celebName")
        assert((stateList[1] as NameDetailUiState.ShowNameDetails).nameDetails.jobTitles[0] == "actor")
        assert((stateList[1] as NameDetailUiState.ShowNameDetails).nameDetails.avatar.value == "avatarImage")
        assert((stateList[1] as NameDetailUiState.ShowNameDetails).nameDetails.bioSummary == "bioSummary")

        coVerify { getNameDetailsUseCase(NameId("nm000000")) }
    }

    @Test
    fun `test get nameDetails network failure`() = runTest{

        coEvery { getNameDetailsUseCase(any()) } returns Result.failure(UnknownHostException())

        viewModel.launchGetNameDetailsUseCase(NameId("nm000000"))

        val stateList = viewModel.nameDetailsUiState.take(2).toList()

        assert(stateList[0] is NameDetailUiState.Loading)
        assert(stateList[1] is NameDetailUiState.NetworkError)

        coVerify { getNameDetailsUseCase(NameId("nm000000")) }
    }

    @Test
    fun `test get nameDetails failure`() = runTest{

        coEvery { getNameDetailsUseCase(any()) } returns Result.failure(RuntimeException())

        viewModel.launchGetNameDetailsUseCase(NameId("nm000000"))

        val stateList = viewModel.nameDetailsUiState.take(2).toList()

        assert(stateList[0] is NameDetailUiState.Loading)
        assert(stateList[1] is NameDetailUiState.Error)

        coVerify { getNameDetailsUseCase(NameId("nm000000")) }
    }


    @Test
    fun `test get nameBioUseCase initial state`() = runTest{
        val state = viewModel.nameBioUiState.first()

        assert(state == NameBioUiState.Loading)
    }

    @Test
    fun `test get nameBioUseCase success`() = runTest{
        val nameBio = mockk<NameBio>()

        every { nameBio getProperty "miniBio" } returns "miniBio"
        every { nameBio getProperty "name" } returns "celebName"
        every { nameBio getProperty "avatar" } returns Image("avatarImage")

        coEvery { getNameBioUseCase(any()) } returns Result.success(nameBio)

        viewModel.launchGetNameBioUseCase(NameId("nm000000"))

        val stateList = viewModel.nameBioUiState.take(2).toList()

        assert(stateList[0] is NameBioUiState.Loading)
        assert(stateList[1] is NameBioUiState.ShowNameBio)
        assert((stateList[1] as NameBioUiState.ShowNameBio).nameBio.miniBio == "miniBio")
        assert((stateList[1] as NameBioUiState.ShowNameBio).nameBio.name == "celebName")
        assert((stateList[1] as NameBioUiState.ShowNameBio).nameBio.avatar.value == "avatarImage")

        coVerify { getNameBioUseCase(NameId("nm000000")) }
    }

    @Test
    fun `test get nameBioUseCase network failure`() = runTest{

        coEvery { getNameBioUseCase(any()) } returns Result.failure(UnknownHostException())

        viewModel.launchGetNameBioUseCase(NameId("nm000000"))

        val stateList = viewModel.nameBioUiState.take(2).toList()

        assert(stateList[0] is NameBioUiState.Loading)
        assert(stateList[1] is NameBioUiState.NetworkError)

        coVerify { getNameBioUseCase(NameId("nm000000")) }
    }

    @Test
    fun `test get nameBioUseCase failure`() = runTest{

        coEvery { getNameBioUseCase(any()) } returns Result.failure(RuntimeException())

        viewModel.launchGetNameBioUseCase(NameId("nm000000"))

        val stateList = viewModel.nameBioUiState.take(2).toList()

        assert(stateList[0] is NameBioUiState.Loading)
        assert(stateList[1] is NameBioUiState.Error)

        coVerify { getNameBioUseCase(NameId("nm000000")) }
    }


    @Test
    fun `test get nameAwardsUseCase initial state`() = runTest{
        val state = viewModel.nameAwardUiState.first()

        assert(state == NameAwardUiState.Loading)
    }

    @Test
    fun `test get nameAwardUiState success`() = runTest{
        val nameAwards = mockk<NameAwards>()

        every { nameAwards getProperty "name" } returns "celebName"
        every { nameAwards getProperty "avatar" } returns Image("avatarImage")

        coEvery { getNameAwardsUseCase(any()) } returns Result.success(nameAwards)

        viewModel.launchGetNameAwardsUseCase(NameId("nm000000"))

        val stateList = viewModel.nameAwardUiState.take(2).toList()

        assert(stateList[0] is NameAwardUiState.Loading)
        assert(stateList[1] is NameAwardUiState.ShowNameAwards)
        assert((stateList[1] as NameAwardUiState.ShowNameAwards).nameAwards.name == "celebName")
        assert((stateList[1] as NameAwardUiState.ShowNameAwards).nameAwards.avatar.value == "avatarImage")

        coVerify { getNameAwardsUseCase(NameId("nm000000")) }
    }

    @Test
    fun `test get nameAwardsUseCase network failure`() = runTest{

        coEvery { getNameAwardsUseCase(any()) } returns Result.failure(UnknownHostException())

        viewModel.launchGetNameAwardsUseCase(NameId("nm000000"))

        val stateList = viewModel.nameAwardUiState.take(2).toList()

        assert(stateList[0] is NameAwardUiState.Loading)
        assert(stateList[1] is NameAwardUiState.NetworkError)

        coVerify { getNameAwardsUseCase(NameId("nm000000")) }
    }

    @Test
    fun `test get nameAwardsUseCase failure`() = runTest{

        coEvery { getNameAwardsUseCase(any()) } returns Result.failure(RuntimeException())

        viewModel.launchGetNameAwardsUseCase(NameId("nm000000"))

        val stateList = viewModel.nameAwardUiState.take(2).toList()

        assert(stateList[0] is NameAwardUiState.Loading)
        assert(stateList[1] is NameAwardUiState.Error)

        coVerify { getNameAwardsUseCase(NameId("nm000000")) }
    }

    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }
}