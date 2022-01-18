package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.SearchRepository
import com.mabahmani.domain.vo.common.Name
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
class AdvancedNameSearchUseCaseTest {

    @MockK
    lateinit var searchRepository: SearchRepository

    @InjectMockKs
    lateinit var advancedNameSearchUseCase: AdvancedNameSearchUseCase

    @RelaxedMockK
    lateinit var listOfNames: List<Name>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test advancedNameSearchUseCase return success`() = runTest{

        coEvery { searchRepository.searchNames() } returns
                Result.success(listOfNames)

        val result = advancedNameSearchUseCase()

        coVerify { searchRepository.searchNames() }

        assert(result.isSuccess)

        assert(result.getOrNull() == listOfNames)
    }

    @Test
    fun `test advancedNameSearchUseCase return failure`() = runTest {

        coEvery { searchRepository.searchNames() } returns
                Result.failure(Throwable("message"))

        val result = advancedNameSearchUseCase()

        coVerify { searchRepository.searchNames() }

        assert(result.isFailure)

        assert(result.exceptionOrNull()?.message == "message")
    }
}