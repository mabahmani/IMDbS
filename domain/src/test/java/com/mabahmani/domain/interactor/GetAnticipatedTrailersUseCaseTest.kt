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
class GetAnticipatedTrailersUseCaseTest {

    @MockK
    lateinit var trailerRepository: TrailerRepository

    @InjectMockKs
    lateinit var getAnticipatedTrailersUseCase: GetAnticipatedTrailersUseCase

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
    fun `test getAnticipatedTrailersUseCase return success`() = runTest{

        coEvery { trailerRepository.getAnticipatedTrailers() } returns
                Result.success(listOfTrailers)

        val result = getAnticipatedTrailersUseCase()

        coVerify { trailerRepository.getAnticipatedTrailers() }

        assert(result.isSuccess)

        assert(result.getOrNull() == listOfTrailers)
    }

    @Test
    fun `test getAnticipatedTrailersUseCase return failure`() = runTest {

        coEvery { trailerRepository.getAnticipatedTrailers() } returns
                Result.failure(Throwable("message"))

        val result = getAnticipatedTrailersUseCase()

        coVerify { trailerRepository.getAnticipatedTrailers() }

        assert(result.isFailure)

        assert(result.exceptionOrNull()?.message == "message")
    }
}