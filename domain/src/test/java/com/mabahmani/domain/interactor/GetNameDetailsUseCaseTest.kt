package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.NameRepository
import com.mabahmani.domain.vo.NameDetails
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
class GetNameDetailsUseCaseTest {

    @MockK
    lateinit var nameRepository: NameRepository

    @InjectMockKs
    lateinit var getNameDetailsUseCase: GetNameDetailsUseCase

    @RelaxedMockK
    lateinit var nameDetails: NameDetails

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test getNameDetailsUseCase return success`() = runTest{

        coEvery { nameRepository.getNameDetails(NameId("nm000000")) } returns
                Result.success(nameDetails)

        val result = getNameDetailsUseCase(NameId("nm000000"))

        coVerify { nameRepository.getNameDetails(NameId("nm000000"))  }

        assert(result.isSuccess)

        assert(result.getOrNull() == nameDetails)
    }

    @Test
    fun `test getNameDetailsUseCase return failure`() = runTest {

        coEvery { nameRepository.getNameDetails(NameId("nm000000")) } returns
                Result.failure(Throwable("message"))

        val result = getNameDetailsUseCase(NameId("nm000000"))

        coVerify { nameRepository.getNameDetails(NameId("nm000000")) }

        assert(result.isFailure)

        assert(result.exceptionOrNull()?.message == "message")
    }
}