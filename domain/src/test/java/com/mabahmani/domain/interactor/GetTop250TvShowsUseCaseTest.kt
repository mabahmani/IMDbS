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
class GetTop250TvShowsUseCaseTest {

    @MockK
    lateinit var chartRepository: ChartRepository

    @InjectMockKs
    lateinit var getTop250TvShowsUseCase: GetTop250TvShowsUseCase

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
    fun `test getTop250TvShowsUseCase return success`() = runTest{

        coEvery { chartRepository.getTop250TvShows() } returns
                Result.success(listOfTitles)

        val result = getTop250TvShowsUseCase()

        coVerify { chartRepository.getTop250TvShows() }

        assert(result.isSuccess)

        assert(result.getOrNull() == listOfTitles)
    }

    @Test
    fun `test getTop250TvShowsUseCase return failure`() = runTest {

        coEvery { chartRepository.getTop250TvShows() } returns
                Result.failure(Throwable("message"))

        val result = getTop250TvShowsUseCase()

        coVerify { chartRepository.getTop250TvShows() }

        assert(result.isFailure)

        assert(result.exceptionOrNull()?.message == "message")
    }
}