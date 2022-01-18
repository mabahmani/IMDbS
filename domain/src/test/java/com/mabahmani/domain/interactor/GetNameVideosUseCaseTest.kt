package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.VideoRepository
import com.mabahmani.domain.vo.common.NameId
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
class GetNameVideosUseCaseTest {

    @MockK
    lateinit var videoRepository: VideoRepository

    @InjectMockKs
    lateinit var getNameVideosUseCase: GetNameVideosUseCase

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
    fun `test getNameVideosUseCase return success`() = runTest{

        coEvery { videoRepository.getNameVideos(nameId =  NameId("nm000000"),"1") } returns
                Result.success(listOfVideos)

        val result = getNameVideosUseCase(nameId = NameId("nm000000"), "1")

        coVerify { videoRepository.getNameVideos(nameId =  NameId("nm000000"),"1")  }

        assert(result.isSuccess)

        assert(result.getOrNull() == listOfVideos)
    }

    @Test
    fun `test getNameVideosUseCase return failure`() = runTest {

        coEvery { videoRepository.getNameVideos(nameId =  NameId("nm000000"),"1")  } returns
                Result.failure(Throwable("message"))

        val result = getNameVideosUseCase(nameId = NameId("nm000000"), "1")

        coVerify { videoRepository.getNameVideos(nameId =  NameId("nm000000"),"1")  }

        assert(result.isFailure)

        assert(result.exceptionOrNull()?.message == "message")
    }
}