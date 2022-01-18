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
class GetTop250MoviesUseCaseTest {

    @MockK
    lateinit var chartRepository: ChartRepository

    @InjectMockKs
    lateinit var getTop250MoviesUseCase: GetTop250MoviesUseCase

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
    fun `test getTop250MoviesUseCase return success`() = runTest{

        coEvery { chartRepository.getTop250Movies() } returns
                Result.success(listOfTitles)

        val result = getTop250MoviesUseCase()

        coVerify { chartRepository.getTop250Movies() }

        assert(result.isSuccess)

        assert(result.getOrNull() == listOfTitles)
    }

    @Test
    fun `test getTop250MoviesUseCase return failure`() = runTest {

        coEvery { chartRepository.getTop250Movies() } returns
                Result.failure(Throwable("message"))

        val result = getTop250MoviesUseCase()

        coVerify { chartRepository.getTop250Movies() }

        assert(result.isFailure)

        assert(result.exceptionOrNull()?.message == "message")
    }
}