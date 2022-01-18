package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.SearchRepository
import com.mabahmani.domain.vo.Calender
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
class GetCalenderUseCaseTest {

    @MockK
    lateinit var searchRepository: SearchRepository

    @InjectMockKs
    lateinit var getCalenderUseCase: GetCalenderUseCase

    @RelaxedMockK
    lateinit var listOfCalenders: List<Calender>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test getCalenderUseCase return success`() = runTest{

        coEvery { searchRepository.getCalender() } returns
                Result.success(listOfCalenders)

        val result = getCalenderUseCase()

        coVerify { searchRepository.getCalender() }

        assert(result.isSuccess)

        assert(result.getOrNull() == listOfCalenders)
    }

    @Test
    fun `test getCalenderUseCase return failure`() = runTest {

        coEvery { searchRepository.getCalender() } returns
                Result.failure(Throwable("message"))

        val result = getCalenderUseCase()

        coVerify { searchRepository.getCalender() }

        assert(result.isFailure)

        assert(result.exceptionOrNull()?.message == "message")
    }
}