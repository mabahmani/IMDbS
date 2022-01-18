package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.ImageRepository
import com.mabahmani.domain.vo.common.ImageLink
import com.mabahmani.domain.vo.common.NameId
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
class GetNameImagesUseCaseTest {

    @MockK
    lateinit var imageRepository: ImageRepository

    @InjectMockKs
    lateinit var getNameImagesUseCase: GetNameImagesUseCase

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
    fun `test getNameImagesUseCase return success`() = runTest{

        coEvery { imageRepository.getNameImages(nameId =  NameId("nm000000"),"1") } returns
                Result.success(listOfImages)

        val result = getNameImagesUseCase(nameId = NameId("nm000000"), "1")

        coVerify { imageRepository.getNameImages(nameId =  NameId("nm000000"),"1")  }

        assert(result.isSuccess)

        assert(result.getOrNull() == listOfImages)
    }

    @Test
    fun `test getNameImagesUseCase return failure`() = runTest {

        coEvery { imageRepository.getNameImages(nameId =  NameId("nm000000"),"1")  } returns
                Result.failure(Throwable("message"))

        val result = getNameImagesUseCase(nameId = NameId("nm000000"), "1")

        coVerify { imageRepository.getNameImages(nameId =  NameId("nm000000"),"1")  }

        assert(result.isFailure)

        assert(result.exceptionOrNull()?.message == "message")
    }
}