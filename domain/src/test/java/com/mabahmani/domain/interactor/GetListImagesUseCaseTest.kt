package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.ImageRepository
import com.mabahmani.domain.vo.common.ImageLink
import com.mabahmani.domain.vo.common.ListId
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
class GetListImagesUseCaseTest {

    @MockK
    lateinit var imageRepository: ImageRepository

    @InjectMockKs
    lateinit var getListImagesUseCase: GetListImagesUseCase

    @RelaxedMockK
    lateinit var listOfImages: List<ImageLink>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test getListImageDetailsUseCase return success`() = runTest{

        coEvery { imageRepository.getListImages(ListId("li000000")) } returns
                Result.success(listOfImages)

        val result = getListImagesUseCase(ListId("li000000"))

        coVerify { imageRepository.getListImages(ListId("li000000"))  }

        assert(result.isSuccess)

        assert(result.getOrNull() == listOfImages)
    }

    @Test
    fun `test getListImageDetailsUseCase return failure`() = runTest {

        coEvery { imageRepository.getListImages(ListId("li000000")) } returns
                Result.failure(Throwable("message"))

        val result = getListImagesUseCase(ListId("li000000"))

        coVerify { imageRepository.getListImages(ListId("li000000")) }

        assert(result.isFailure)

        assert(result.exceptionOrNull()?.message == "message")
    }
}