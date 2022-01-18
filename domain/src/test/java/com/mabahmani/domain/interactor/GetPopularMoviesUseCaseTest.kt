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
class GetPopularMoviesUseCaseTest {

    @MockK
    lateinit var chartRepository: ChartRepository

    @InjectMockKs
    lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase

    @RelaxedMockK
    lateinit var listOfMovies: List<Title>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test getPopularMoviesUseCase return success`() = runTest{

        coEvery { chartRepository.getPopularMovies() } returns
                Result.success(listOfMovies)

        val result = getPopularMoviesUseCase()

        coVerify { chartRepository.getPopularMovies() }

        assert(result.isSuccess)

        assert(result.getOrNull() == listOfMovies)
    }

    @Test
    fun `test getPopularMoviesUseCase return failure`() = runTest {

        coEvery { chartRepository.getPopularMovies()  } returns
                Result.failure(Throwable("message"))

        val result = getPopularMoviesUseCase()

        coVerify { chartRepository.getPopularMovies()  }

        assert(result.isFailure)

        assert(result.exceptionOrNull()?.message == "message")
    }
}