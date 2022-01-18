package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.TitleRepository
import com.mabahmani.domain.vo.TitleParentsGuide
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
class GetTitleParentsGuidesUseCaseTest {

    @MockK
    lateinit var titleRepository: TitleRepository

    @InjectMockKs
    lateinit var getTitleParentsGuideUseCase: GetTitleParentsGuideUseCase

    @RelaxedMockK
    lateinit var titleParentsGuide: TitleParentsGuide

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test getTitleParentsGuideUseCase return success`() = runTest{

        coEvery { titleRepository.getTitleParentsGuide(TitleId("tt000000")) } returns
                Result.success(titleParentsGuide)

        val result = getTitleParentsGuideUseCase(TitleId("tt000000"))

        coVerify {  titleRepository.getTitleParentsGuide(TitleId("tt000000"))  }

        assert(result.isSuccess)

        assert(result.getOrNull() == titleParentsGuide)
    }

    @Test
    fun `test getTitleParentsGuideUseCase return failure`() = runTest {

        coEvery { titleRepository.getTitleParentsGuide(TitleId("tt000000"))   } returns
                Result.failure(Throwable("message"))

        val result = getTitleParentsGuideUseCase(TitleId("tt000000"))

        coVerify {  titleRepository.getTitleParentsGuide(TitleId("tt000000"))  }

        assert(result.isFailure)

        assert(result.exceptionOrNull()?.message == "message")
    }
}