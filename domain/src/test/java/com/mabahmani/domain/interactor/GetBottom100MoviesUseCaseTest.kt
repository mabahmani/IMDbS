package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.ChartRepository
import com.mabahmani.domain.vo.common.Title
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
class GetBottom100MoviesUseCaseTest {

    @MockK
    lateinit var chartRepository: ChartRepository

    @InjectMockKs
    lateinit var getBottom100MoviesUseCase: GetBottom100MoviesUseCase

    @RelaxedMockK
    lateinit var listOfTitles: List<Title>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test getBottom100MoviesUseCase return success`() = runTest{

        coEvery { chartRepository.getBottom100Movies() } returns
                Result.success(listOfTitles)

        val result = getBottom100MoviesUseCase()

        coVerify { chartRepository.getBottom100Movies() }

        assert(result.isSuccess)

        assert(result.getOrNull() == listOfTitles)
    }

    @Test
    fun `test getBottom100MoviesUseCase return failure`() = runTest {

        coEvery { chartRepository.getBottom100Movies() } returns
                Result.failure(Throwable("message"))

        val result = getBottom100MoviesUseCase()

        coVerify { chartRepository.getBottom100Movies() }

        assert(result.isFailure)

        assert(result.exceptionOrNull()?.message == "message")
    }
}