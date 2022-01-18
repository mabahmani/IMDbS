package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.SearchRepository
import com.mabahmani.domain.vo.common.Genre
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
class GetGenresUseCaseTest {

    @MockK
    lateinit var searchRepository: SearchRepository

    @InjectMockKs
    lateinit var getGenresUseCase: GetGenresUseCase

    @RelaxedMockK
    lateinit var listOfGenres: List<Genre>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test getGenresUseCase return success`() = runTest{

        coEvery { searchRepository.getGenres() } returns
                Result.success(listOfGenres)

        val result = getGenresUseCase()

        coVerify { searchRepository.getGenres()  }

        assert(result.isSuccess)

        assert(result.getOrNull() == listOfGenres)
    }

    @Test
    fun `test getGenresUseCase return failure`() = runTest {

        coEvery { searchRepository.getGenres()  } returns
                Result.failure(Throwable("message"))

        val result = getGenresUseCase()

        coVerify { searchRepository.getGenres()  }

        assert(result.isFailure)

        assert(result.exceptionOrNull()?.message == "message")
    }
}