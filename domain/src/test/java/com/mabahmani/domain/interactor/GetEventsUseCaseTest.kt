package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.SearchRepository
import com.mabahmani.domain.vo.common.Event
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
class GetEventsUseCaseTest {

    @MockK
    lateinit var searchRepository: SearchRepository

    @InjectMockKs
    lateinit var getEventsUseCase: GetEventsUseCase

    @RelaxedMockK
    lateinit var listOfEvents: List<Event>

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

        coEvery { searchRepository.getEvents() } returns
                Result.success(listOfEvents)

        val result = getEventsUseCase()

        coVerify { searchRepository.getEvents()  }

        assert(result.isSuccess)

        assert(result.getOrNull() == listOfEvents)
    }

    @Test
    fun `test getEventDetailsUseCase return failure`() = runTest {

        coEvery { searchRepository.getEvents()  } returns
                Result.failure(Throwable("message"))

        val result = getEventsUseCase()

        coVerify { searchRepository.getEvents()  }

        assert(result.isFailure)

        assert(result.exceptionOrNull()?.message == "message")
    }
}