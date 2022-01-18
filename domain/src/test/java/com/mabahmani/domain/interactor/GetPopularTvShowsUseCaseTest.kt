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
class GetPopularTvShowsUseCaseTest {

    @MockK
    lateinit var chartRepository: ChartRepository

    @InjectMockKs
    lateinit var getPopularTvShowsUseCase: GetPopularTvShowsUseCase

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
    fun `test getPopularTvShowsUseCase return success`() = runTest{

        coEvery { chartRepository.getPopularTvShows() } returns
                Result.success(listOfMovies)

        val result = getPopularTvShowsUseCase()

        coVerify {  chartRepository.getPopularTvShows() }

        assert(result.isSuccess)

        assert(result.getOrNull() == listOfMovies)
    }

    @Test
    fun `test getPopularTvShowsUseCase return failure`() = runTest {

        coEvery { chartRepository.getPopularTvShows()  } returns
                Result.failure(Throwable("message"))

        val result = getPopularTvShowsUseCase()

        coVerify {  chartRepository.getPopularTvShows()   }

        assert(result.isFailure)

        assert(result.exceptionOrNull()?.message == "message")
    }
}