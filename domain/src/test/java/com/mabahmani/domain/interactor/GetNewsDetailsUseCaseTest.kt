package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.NewsRepository
import com.mabahmani.domain.vo.NewsDetails
import com.mabahmani.domain.vo.common.NewsId
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
class GetNewsDetailsUseCaseTest {

    @MockK
    lateinit var newsRepository: NewsRepository

    @InjectMockKs
    lateinit var getNewsDetailsUseCase: GetNewsDetailsUseCase

    @RelaxedMockK
    lateinit var newsDetails: NewsDetails

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test getNewsDetailsUseCase return success`() = runTest{

        coEvery { newsRepository.getNewsDetails(NewsId("ni000000")) } returns
                Result.success(newsDetails)

        val result = getNewsDetailsUseCase(NewsId("ni000000"))

        coVerify { newsRepository.getNewsDetails(NewsId("ni000000")) }

        assert(result.isSuccess)

        assert(result.getOrNull() == newsDetails)
    }

    @Test
    fun `test getNewsDetailsUseCase return failure`() = runTest {

        coEvery { newsRepository.getNewsDetails(NewsId("ni000000"))  } returns
                Result.failure(Throwable("message"))

        val result = getNewsDetailsUseCase(NewsId("ni000000"))

        coVerify { newsRepository.getNewsDetails(NewsId("ni000000"))  }

        assert(result.isFailure)

        assert(result.exceptionOrNull()?.message == "message")
    }
}