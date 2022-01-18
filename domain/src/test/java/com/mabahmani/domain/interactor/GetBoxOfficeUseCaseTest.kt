package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.ChartRepository
import com.mabahmani.domain.vo.common.BoxOffice
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
class GetBoxOfficeUseCaseTest {

    @MockK
    lateinit var chartRepository: ChartRepository

    @InjectMockKs
    lateinit var getBoxOfficeUseCase: GetBoxOfficeUseCase

    @RelaxedMockK
    lateinit var boxOffice: BoxOffice

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test getBoxOfficeUseCase return success`() = runTest{

        coEvery { chartRepository.getBoxOffice() } returns
                Result.success(boxOffice)

        val result = getBoxOfficeUseCase()

        coVerify { chartRepository.getBoxOffice() }

        assert(result.isSuccess)

        assert(result.getOrNull() == boxOffice)
    }

    @Test
    fun `test getBoxOfficeUseCase return failure`() = runTest {

        coEvery { chartRepository.getBoxOffice() } returns
                Result.failure(Throwable("message"))

        val result = getBoxOfficeUseCase()

        coVerify { chartRepository.getBoxOffice() }

        assert(result.isFailure)

        assert(result.exceptionOrNull()?.message == "message")
    }
}