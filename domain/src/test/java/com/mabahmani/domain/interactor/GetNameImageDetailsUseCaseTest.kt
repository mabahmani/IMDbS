package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.ImageRepository
import com.mabahmani.domain.vo.ImageDetails
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
class GetNameImageDetailsUseCaseTest {

    @MockK
    lateinit var imageRepository: ImageRepository

    @InjectMockKs
    lateinit var getNameImageDetailsUseCase: GetNameImageDetailsUseCase

    @RelaxedMockK
    lateinit var imageDetails: ImageDetails

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test getNameImageDetailsUseCase return success`() = runTest{

        coEvery { imageRepository.getNameImagesWithDetails(nameId =  NameId("nm000000")) } returns
                Result.success(imageDetails)

        val result = getNameImageDetailsUseCase(nameId = NameId("nm000000"))

        coVerify { imageRepository.getNameImagesWithDetails(nameId =  NameId("nm000000"))  }

        assert(result.isSuccess)

        assert(result.getOrNull() == imageDetails)
    }

    @Test
    fun `test getNameImageDetailsUseCase return failure`() = runTest {

        coEvery { imageRepository.getNameImagesWithDetails(nameId =  NameId("nm000000")) } returns
                Result.failure(Throwable("message"))

        val result = getNameImageDetailsUseCase(nameId = NameId("nm000000"))

        coVerify { imageRepository.getNameImagesWithDetails(nameId =  NameId("nm000000")) }

        assert(result.isFailure)

        assert(result.exceptionOrNull()?.message == "message")
    }
}