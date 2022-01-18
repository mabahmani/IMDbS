package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.VideoRepository
import com.mabahmani.domain.vo.common.TitleId
import com.mabahmani.domain.vo.common.Video
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
class GetTitleVideosUseCaseTest {

    @MockK
    lateinit var videoRepository: VideoRepository

    @InjectMockKs
    lateinit var getTitleVideosUseCase: GetTitleVideosUseCase

    @RelaxedMockK
    lateinit var listOfVideos: List<Video>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test getTitleTechnicalSpecsUseCase return success`() = runTest{

        coEvery { videoRepository.getTitleVideos(TitleId("tt000000"), "1") } returns
                Result.success(listOfVideos)

        val result = getTitleVideosUseCase(TitleId("tt000000") ,"1")

        coVerify { videoRepository.getTitleVideos(TitleId("tt000000"), "1")   }

        assert(result.isSuccess)

        assert(result.getOrNull() == listOfVideos)
    }

    @Test
    fun `test getTitleTechnicalSpecsUseCase return failure`() = runTest {

        coEvery { videoRepository.getTitleVideos(TitleId("tt000000"), "1") } returns
                Result.failure(Throwable("message"))

        val result = getTitleVideosUseCase(TitleId("tt000000"), "1")

        coVerify { videoRepository.getTitleVideos(TitleId("tt000000"), "1") }

        assert(result.isFailure)

        assert(result.exceptionOrNull()?.message == "message")
    }
}