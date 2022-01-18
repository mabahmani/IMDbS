package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.TrailerRepository
import com.mabahmani.domain.vo.common.Trailer
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
class GetTrendingTrailersUseCaseTest {

    @MockK
    lateinit var trailerRepository: TrailerRepository

    @InjectMockKs
    lateinit var getTrendingTrailersUseCase: GetTrendingTrailersUseCase

    @RelaxedMockK
    lateinit var listOfTrailers: List<Trailer>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test getTrendingTrailersUseCase return success`() = runTest{

        coEvery { trailerRepository.getTrendingTrailers() } returns
                Result.success(listOfTrailers)

        val result = getTrendingTrailersUseCase()

        coVerify { trailerRepository.getTrendingTrailers() }

        assert(result.isSuccess)

        assert(result.getOrNull() == listOfTrailers)
    }

    @Test
    fun `test getTrendingTrailersUseCase return failure`() = runTest {

        coEvery { trailerRepository.getTrendingTrailers() } returns
                Result.failure(Throwable("message"))

        val result = getTrendingTrailersUseCase()

        coVerify { trailerRepository.getTrendingTrailers() }

        assert(result.isFailure)

        assert(result.exceptionOrNull()?.message == "message")
    }
}