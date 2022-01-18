package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.HomeRepository
import com.mabahmani.domain.vo.HomeExtra
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
class GetHomeExtraUseCaseTest {

    @MockK
    lateinit var homeRepository: HomeRepository

    @InjectMockKs
    lateinit var getHomeExtraUseCase: GetHomeExtraUseCase

    @RelaxedMockK
    lateinit var homeExtra: HomeExtra

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test getHomeExtraUseCase return success`() = runTest{

        coEvery { homeRepository.getHomeExtra() } returns
                Result.success(homeExtra)

        val result = getHomeExtraUseCase()

        coVerify { homeRepository.getHomeExtra()  }

        assert(result.isSuccess)

        assert(result.getOrNull() == homeExtra)
    }

    @Test
    fun `test getHomeExtraUseCase return failure`() = runTest {

        coEvery { homeRepository.getHomeExtra()  } returns
                Result.failure(Throwable("message"))

        val result = getHomeExtraUseCase()

        coVerify { homeRepository.getHomeExtra()  }

        assert(result.isFailure)

        assert(result.exceptionOrNull()?.message == "message")
    }
}