package com.mabahmani.data.repo

import com.mabahmani.data.ds.RemoteDataSourceImpl
import com.mabahmani.data.vo.generic.ApiResponse
import com.mabahmani.data.vo.res.VideoDetailsRes
import com.mabahmani.data.vo.res.VideosRes
import com.mabahmani.domain.vo.common.NameId
import com.mabahmani.domain.vo.common.TitleId
import com.mabahmani.domain.vo.common.VideoId
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class VideoRepositoryImplTest {
    @MockK
    lateinit var remoteDataSourceImpl: RemoteDataSourceImpl

    @InjectMockKs
    lateinit var videoRepositoryImpl: VideoRepositoryImpl

    @RelaxedMockK
    lateinit var videoDetailsRes: VideoDetailsRes

    @RelaxedMockK
    lateinit var videosRes: VideosRes

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun getVideoDetails() = runTest {
        coEvery { remoteDataSourceImpl.getVideoDetails(any()) } returns
                Result.success(
                    ApiResponse(videoDetailsRes, true, "")
                )

        val result = videoRepositoryImpl.getVideoDetails(VideoId("vi000000"))

        coVerify { remoteDataSourceImpl.getVideoDetails("vi000000") }

        assert(result.isSuccess)
    }

    @Test
    fun getTitleVideos() = runTest {
        coEvery { remoteDataSourceImpl.getTitleVideos(any(), any()) } returns
                Result.success(
                    ApiResponse(videosRes, true, "")
                )

        val result = videoRepositoryImpl.getTitleVideos(TitleId("tt000000"), "5")

        coVerify { remoteDataSourceImpl.getTitleVideos("tt000000", "5") }

        assert(result.isSuccess)
    }

    @Test
    fun getNameVideos() = runTest {
        coEvery { remoteDataSourceImpl.getNameVideos(any(), any()) } returns
                Result.success(
                    ApiResponse(videosRes, true, "")
                )

        val result = videoRepositoryImpl.getNameVideos(NameId("nm000000"), "5")

        coVerify { remoteDataSourceImpl.getNameVideos("nm000000", "5") }

        assert(result.isSuccess)
    }
}