package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.SearchRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetKeywordsUseCaseTest {

    @MockK
    lateinit var searchRepository: SearchRepository

    @InjectMockKs
    lateinit var getKeywordsUseCase: GetKeywordsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test getKeywordsUseCase return success`() = runTest{

        coEvery { searchRepository.getKeywords() } returns
                Result.success(listOf("keys"))

        val result = getKeywordsUseCase()

        coVerify { searchRepository.getKeywords()  }

        assert(result.isSuccess)

        assert(result.getOrNull() == listOf("keys"))
    }

    @Test
    fun `test getKeywordsUseCase return failure`() = runTest {

        coEvery { searchRepository.getKeywords()  } returns
                Result.failure(Throwable("message"))

        val result = getKeywordsUseCase()

        coVerify { searchRepository.getKeywords()  }

        assert(result.isFailure)

        assert(result.exceptionOrNull()?.message == "message")
    }
}