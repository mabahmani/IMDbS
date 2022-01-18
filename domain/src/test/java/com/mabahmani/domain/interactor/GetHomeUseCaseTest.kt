package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.HomeRepository
import com.mabahmani.domain.vo.Home
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
class GetHomeUseCaseTest {

    @MockK
    lateinit var homeRepository: HomeRepository

    @InjectMockKs
    lateinit var getHomeUseCase: GetHomeUseCase

    @RelaxedMockK
    lateinit var home: Home

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test getHomeUseCase return success`() = runTest{

        coEvery { homeRepository.getHome() } returns
                Result.success(home)

        val result = getHomeUseCase()

        coVerify { homeRepository.getHome()  }

        assert(result.isSuccess)

        assert(result.getOrNull() == home)
    }

    @Test
    fun `test getHomeUseCase return failure`() = runTest {

        coEvery { homeRepository.getHome()  } returns
                Result.failure(Throwable("message"))

        val result = getHomeUseCase()

        coVerify { homeRepository.getHome()  }

        assert(result.isFailure)

        assert(result.exceptionOrNull()?.message == "message")
    }
}