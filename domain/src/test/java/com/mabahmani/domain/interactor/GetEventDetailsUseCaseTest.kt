package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.EventRepository
import com.mabahmani.domain.vo.EventDetails
import com.mabahmani.domain.vo.common.EventId
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetEventDetailsUseCaseTest {

    @MockK
    lateinit var eventRepository: EventRepository

    @InjectMockKs
    lateinit var getEventDetailsUseCase: GetEventDetailsUseCase

    @RelaxedMockK
    lateinit var eventDetails: EventDetails

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test getEventDetailsUseCase return success`() = runTest{

        coEvery { eventRepository.getEventDetails(EventId("ev000000"), 1991) } returns
                Result.success(eventDetails)

        val result = getEventDetailsUseCase(EventId("ev000000"), 1991)

        coVerify { eventRepository.getEventDetails(EventId("ev000000"), 1991) }

        assert(result.isSuccess)

        assert(result.getOrNull() == eventDetails)
    }

    @Test
    fun `test getEventDetailsUseCase return failure`() = runTest {

        coEvery { eventRepository.getEventDetails(any(), any()) } returns
                Result.failure(Throwable("message"))

        val result = getEventDetailsUseCase(EventId("ev000000"), 1991)

        coVerify { eventRepository.getEventDetails(EventId("ev000000"), 1991) }

        assert(result.isFailure)

        assert(result.exceptionOrNull()?.message == "message")
    }
}