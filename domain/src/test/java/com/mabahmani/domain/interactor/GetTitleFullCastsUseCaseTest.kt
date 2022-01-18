package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.TitleRepository
import com.mabahmani.domain.vo.TitleFullCasts
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
class GetTitleFullCastsUseCaseTest {

    @MockK
    lateinit var titleRepository: TitleRepository

    @InjectMockKs
    lateinit var getTitleFullCastsUseCase: GetTitleFullCastsUseCase

    @RelaxedMockK
    lateinit var titleFullCasts:TitleFullCasts

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test getTitleFullCastsUseCase return success`() = runTest{

        coEvery { titleRepository.getTitleFullCasts(TitleId("tt000000")) } returns
                Result.success(titleFullCasts)

        val result = getTitleFullCastsUseCase(TitleId("tt000000"))

        coVerify {  titleRepository.getTitleFullCasts(TitleId("tt000000"))  }

        assert(result.isSuccess)

        assert(result.getOrNull() == titleFullCasts)
    }

    @Test
    fun `test getTitleFullCastsUseCase return failure`() = runTest {

        coEvery { titleRepository.getTitleFullCasts(TitleId("tt000000"))   } returns
                Result.failure(Throwable("message"))

        val result = getTitleFullCastsUseCase(TitleId("tt000000"))

        coVerify {  titleRepository.getTitleFullCasts(TitleId("tt000000"))  }

        assert(result.isFailure)

        assert(result.exceptionOrNull()?.message == "message")
    }
}