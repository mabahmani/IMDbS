package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.TitleRepository
import com.mabahmani.domain.vo.TitleAwards
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
class GetTitleAwardsUseCaseTest {

    @MockK
    lateinit var titleRepository: TitleRepository

    @InjectMockKs
    lateinit var getTitleAwardsUseCase: GetTitleAwardsUseCase

    @RelaxedMockK
    lateinit var titleAwards:TitleAwards

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test getTitleAwardsUseCase return success`() = runTest{

        coEvery { titleRepository.getTitleAwards(TitleId("tt000000")) } returns
                Result.success(titleAwards)

        val result = getTitleAwardsUseCase(TitleId("tt000000"))

        coVerify {  titleRepository.getTitleAwards(TitleId("tt000000"))  }

        assert(result.isSuccess)

        assert(result.getOrNull() == titleAwards)
    }

    @Test
    fun `test getTitleAwardsUseCase return failure`() = runTest {

        coEvery { titleRepository.getTitleAwards(TitleId("tt000000"))   } returns
                Result.failure(Throwable("message"))

        val result = getTitleAwardsUseCase(TitleId("tt000000"))

        coVerify {  titleRepository.getTitleAwards(TitleId("tt000000"))  }

        assert(result.isFailure)

        assert(result.exceptionOrNull()?.message == "message")
    }
}