package com.mabahmani.imdb_scraping.vm

import com.mabahmani.domain.interactor.GetEventDetailsUseCase
import com.mabahmani.domain.vo.EventDetails
import com.mabahmani.domain.vo.common.EventId
import com.mabahmani.imdb_scraping.ui.main.event.state.EventDetailsUiState
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
class EventViewModelTest {

    @RelaxedMockK
    lateinit var getEventDetailsUseCase: GetEventDetailsUseCase

    @InjectMockKs
    private lateinit var viewModel: EventViewModel

    private lateinit var coroutineDispatcher: TestDispatcher


    @Before
    fun setUp() {
        coroutineDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(coroutineDispatcher)
        MockKAnnotations.init(this)
    }

    @Test
    fun `test getEventDetailsUseCase initial state`() = runTest{
        val state = viewModel.eventDetailsUiState.first()

        assert(state == EventDetailsUiState.Loading)
    }

    @Test
    fun `test getEventDetailsUseCase success`() = runTest{
        val eventDetails = mockk<EventDetails>()
        val award = mockk<EventDetails.Award>()

        every { award getProperty "awardName" } returns "awardName"


        every { eventDetails getProperty "eventName" } returns "eventName"
        every { eventDetails getProperty "eventCaption" } returns "eventCaption"
        every { eventDetails getProperty "eventYear" } returns "eventYear"
        every { eventDetails getProperty "awards" } returns listOf(award)

        coEvery { getEventDetailsUseCase(any(), any()) } returns Result.success(eventDetails)

        viewModel.launchGetEventDetailsUseCase(EventId("ev000000"),2022)

        val stateList = viewModel.eventDetailsUiState.take(2).toList()

        assert(stateList[0] is EventDetailsUiState.Loading)
        assert(stateList[1] is EventDetailsUiState.ShowEventDetailsData)
        assert((stateList[1] as EventDetailsUiState.ShowEventDetailsData).eventDetails.eventName == "eventName")
        assert((stateList[1] as EventDetailsUiState.ShowEventDetailsData).eventDetails.eventCaption == "eventCaption")
        assert((stateList[1] as EventDetailsUiState.ShowEventDetailsData).eventDetails.eventYear == "eventYear")
        assert((stateList[1] as EventDetailsUiState.ShowEventDetailsData).eventDetails.awards[0].awardName == "awardName")

        coVerify { getEventDetailsUseCase(EventId("ev000000"),2022) }
    }

    @Test
    fun `test getEventDetailsUseCase network failure`() = runTest{

        coEvery { getEventDetailsUseCase(any(), any()) } returns Result.failure(UnknownHostException())

        viewModel.launchGetEventDetailsUseCase(EventId("ev000000"),2022)

        val stateList = viewModel.eventDetailsUiState.take(2).toList()

        assert(stateList[0] is EventDetailsUiState.Loading)
        assert(stateList[1] is EventDetailsUiState.NetworkError)

        coVerify { getEventDetailsUseCase(EventId("ev000000"),2022) }
    }

    @Test
    fun `test getEventDetailsUseCase failure`() = runTest{

        coEvery { getEventDetailsUseCase(any(), any()) } returns Result.failure(RuntimeException())

        viewModel.launchGetEventDetailsUseCase(EventId("ev000000"),2022)

        val stateList = viewModel.eventDetailsUiState.take(2).toList()

        assert(stateList[0] is EventDetailsUiState.Loading)
        assert(stateList[1] is EventDetailsUiState.Error)

        coVerify { getEventDetailsUseCase(EventId("ev000000"),2022) }
    }

    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }
}