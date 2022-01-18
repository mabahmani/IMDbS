package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.TitleRepository
import com.mabahmani.domain.vo.TitleTechnicalSpecs
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
class GetTitleTechnicalSpecsUseCaseTest {

    @MockK
    lateinit var titleRepository: TitleRepository

    @InjectMockKs
    lateinit var getTitleTechnicalSpecsUseCase: GetTitleTechnicalSpecsUseCase

    @RelaxedMockK
    lateinit var titleTechnicalSpecs: TitleTechnicalSpecs

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

        coEvery { titleRepository.getTitleTechnicalSpecs(TitleId("tt000000")) } returns
                Result.success(titleTechnicalSpecs)

        val result = getTitleTechnicalSpecsUseCase(TitleId("tt000000"))

        coVerify {  titleRepository.getTitleTechnicalSpecs(TitleId("tt000000"))  }

        assert(result.isSuccess)

        assert(result.getOrNull() == titleTechnicalSpecs)
    }

    @Test
    fun `test getTitleTechnicalSpecsUseCase return failure`() = runTest {

        coEvery { titleRepository.getTitleTechnicalSpecs(TitleId("tt000000"))   } returns
                Result.failure(Throwable("message"))

        val result = getTitleTechnicalSpecsUseCase(TitleId("tt000000"))

        coVerify {  titleRepository.getTitleTechnicalSpecs(TitleId("tt000000"))  }

        assert(result.isFailure)

        assert(result.exceptionOrNull()?.message == "message")
    }
}