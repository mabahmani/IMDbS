package com.mabahmani.data.repo

import com.mabahmani.data.ds.RemoteDataSourceImpl
import com.mabahmani.data.vo.generic.ApiResponse
import com.mabahmani.domain.vo.common.TitleId
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class TrailerRepositoryImplTest {
    @MockK
    lateinit var remoteDataSourceImpl: RemoteDataSourceImpl

    @InjectMockKs
    lateinit var trailerRepositoryImpl: TrailerRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun getAnticipatedTrailers() = runTest {
        coEvery { remoteDataSourceImpl.getTrailersAnticipated() } returns
                Result.success(
                    ApiResponse(listOf(), true, "")
                )

        val result = trailerRepositoryImpl.getAnticipatedTrailers()

        coVerify { remoteDataSourceImpl.getTrailersAnticipated() }

        assert(result.isSuccess)
    }

    @Test
    fun getPopularTrailers() = runTest {
        coEvery { remoteDataSourceImpl.getTrailersPopular() } returns
                Result.success(
                    ApiResponse(listOf(), true, "")
                )

        val result = trailerRepositoryImpl.getPopularTrailers()

        coVerify { remoteDataSourceImpl.getTrailersPopular() }

        assert(result.isSuccess)
    }

    @Test
    fun getRecentTrailers() = runTest {
        coEvery { remoteDataSourceImpl.getTrailersRecent() } returns
                Result.success(
                    ApiResponse(listOf(), true, "")
                )

        val result = trailerRepositoryImpl.getRecentTrailers()

        coVerify { remoteDataSourceImpl.getTrailersRecent() }

        assert(result.isSuccess)
    }

    @Test
    fun getTrendingTrailers() = runTest {
        coEvery { remoteDataSourceImpl.getTrailersTrending() } returns
                Result.success(
                    ApiResponse(listOf(), true, "")
                )

        val result = trailerRepositoryImpl.getTrendingTrailers()

        coVerify { remoteDataSourceImpl.getTrailersTrending() }

        assert(result.isSuccess)
    }
}