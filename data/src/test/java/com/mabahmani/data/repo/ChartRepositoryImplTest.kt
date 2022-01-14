package com.mabahmani.data.repo

import com.mabahmani.data.ds.RemoteDataSourceImpl
import com.mabahmani.data.vo.generic.ApiResponse
import com.mabahmani.data.vo.res.ChartBoxOfficeRes
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ChartRepositoryImplTest {

    @MockK
    lateinit var remoteDataSourceImpl: RemoteDataSourceImpl

    @InjectMockKs
    lateinit var chartRepositoryImpl: ChartRepositoryImpl

    @RelaxedMockK
    lateinit var chartBoxOfficeRes: ChartBoxOfficeRes

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun getTop250Movies() = runTest {
        coEvery { remoteDataSourceImpl.getChartTop250() } returns
                Result.success(
                    ApiResponse(listOf(), true, "")
                )

        val result = chartRepositoryImpl.getTop250Movies()

        coVerify { remoteDataSourceImpl.getChartTop250() }

        assert(result.isSuccess)
    }

    @Test
    fun getTop250TvShows() = runTest {
        coEvery { remoteDataSourceImpl.getChartTopTv250() } returns
                Result.success(
                    ApiResponse(listOf(), true, "")
                )

        val result = chartRepositoryImpl.getTop250TvShows()

        coVerify { remoteDataSourceImpl.getChartTopTv250() }

        assert(result.isSuccess)
    }

    @Test
    fun getPopularMovies() = runTest {
        coEvery { remoteDataSourceImpl.getChartPopular() } returns
                Result.success(
                    ApiResponse(listOf(), true, "")
                )

        val result = chartRepositoryImpl.getPopularMovies()

        coVerify { remoteDataSourceImpl.getChartPopular() }

        assert(result.isSuccess)
    }

    @Test
    fun getPopularTvShows() = runTest {
        coEvery { remoteDataSourceImpl.getChartTvPopular() } returns
                Result.success(
                    ApiResponse(listOf(), true, "")
                )

        val result = chartRepositoryImpl.getPopularTvShows()

        coVerify { remoteDataSourceImpl.getChartTvPopular() }

        assert(result.isSuccess)
    }

    @Test
    fun getBottom100Movies() = runTest {
        coEvery { remoteDataSourceImpl.getChartBottom100() } returns
                Result.success(
                    ApiResponse(listOf(), true, "")
                )

        val result = chartRepositoryImpl.getBottom100Movies()

        coVerify { remoteDataSourceImpl.getChartBottom100() }

        assert(result.isSuccess)
    }

    @Test
    fun getBoxOffice() = runTest {
        coEvery { remoteDataSourceImpl.getChartBoxOffice() } returns
                Result.success(
                    ApiResponse(chartBoxOfficeRes, true, "")
                )

        val result = chartRepositoryImpl.getBoxOffice()

        coVerify { remoteDataSourceImpl.getChartBoxOffice() }

        assert(result.isSuccess)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}