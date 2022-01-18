package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.VideoRepository
import com.mabahmani.domain.vo.VideoDetails
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
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetVideoDetailsUseCaseTest {

    @MockK
    lateinit var videoRepository: VideoRepository

    @InjectMockKs
    lateinit var getVideoDetailsUseCase: GetVideoDetailsUseCase

    @RelaxedMockK
    lateinit var videoDetails: VideoDetails

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test getVideoDetailsUseCase return success`() = runTest{

        coEvery { videoRepository.getVideoDetails(VideoId("vi000000"))} returns
                Result.success(videoDetails)

        val result = getVideoDetailsUseCase(VideoId("vi000000"))

        coVerify { videoRepository.getVideoDetails(VideoId("vi000000"))  }

        assert(result.isSuccess)

        assert(result.getOrNull() == videoDetails)
    }

    @Test
    fun `test getVideoDetailsUseCase return failure`() = runTest {

        coEvery {  videoRepository.getVideoDetails(VideoId("vi000000"))} returns
                Result.failure(Throwable("message"))

        val result = getVideoDetailsUseCase(VideoId("vi000000"))

        coVerify {  videoRepository.getVideoDetails(VideoId("vi000000")) }

        assert(result.isFailure)

        assert(result.exceptionOrNull()?.message == "message")
    }
}