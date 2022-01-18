package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.SearchRepository
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
class GetTitlesUseCaseTest {

    @MockK
    lateinit var searchRepository: SearchRepository

    @InjectMockKs
    lateinit var getTitlesUseCase: GetTitlesUseCase

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
    fun `test getTitlesUseCase return success`() = runTest{

        coEvery { searchRepository.searchTitles() } returns
                Result.success(listOfTitles)

        val result = getTitlesUseCase()

        coVerify { searchRepository.searchTitles()  }

        assert(result.isSuccess)

        assert(result.getOrNull() == listOfTitles)
    }

    @Test
    fun `test getTitlesUseCase return failure`() = runTest {

        coEvery { searchRepository.searchTitles()  } returns
                Result.failure(Throwable("message"))

        val result = getTitlesUseCase()

        coVerify { searchRepository.searchTitles()  }

        assert(result.isFailure)

        assert(result.exceptionOrNull()?.message == "message")
    }
}