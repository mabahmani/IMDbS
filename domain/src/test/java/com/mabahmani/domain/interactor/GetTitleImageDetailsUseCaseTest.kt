package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.ImageRepository
import com.mabahmani.domain.vo.ImageDetails
import com.mabahmani.domain.vo.common.TitleId
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
class GetTitleImageDetailsUseCaseTest {

    @MockK
    lateinit var imageRepository: ImageRepository

    @InjectMockKs
    lateinit var getTitleImageDetailsUseCase: GetTitleImageDetailsUseCase

    @RelaxedMockK
    lateinit var imageDetails:ImageDetails

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test getTitleImageDetailsUseCase return success`() = runTest{

        coEvery { imageRepository.getTitleImagesWithDetails( titleId = TitleId("tt000000")) } returns
                Result.success(imageDetails)

        val result = getTitleImageDetailsUseCase(titleId = TitleId("tt000000"))

        coVerify { imageRepository.getTitleImagesWithDetails( titleId = TitleId("tt000000"))   }

        assert(result.isSuccess)

        assert(result.getOrNull() == imageDetails)
    }

    @Test
    fun `test getTitleImageDetailsUseCase return failure`() = runTest {

        coEvery { imageRepository.getTitleImagesWithDetails( titleId = TitleId("tt000000"))  } returns
                Result.failure(Throwable("message"))

        val result = getTitleImageDetailsUseCase(titleId = TitleId("tt000000"))

        coVerify {imageRepository.getTitleImagesWithDetails( titleId = TitleId("tt000000")) }

        assert(result.isFailure)

        assert(result.exceptionOrNull()?.message == "message")
    }
}