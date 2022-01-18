package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.SearchRepository
import com.mabahmani.domain.vo.Suggestion
import com.mabahmani.domain.vo.enum.SuggestionType
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
class SuggestTitleUseCaseTest {

    @MockK
    lateinit var searchRepository: SearchRepository

    @InjectMockKs
    lateinit var suggestTitleUseCase: SuggestTitleUseCase

    @RelaxedMockK
    lateinit var listOfSuggestions: List<Suggestion>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test suggestTitleUseCase return success`() = runTest{

        coEvery { searchRepository.getSuggestion(SuggestionType.TITLE, "harry") } returns
                Result.success(listOfSuggestions)

        val result = suggestTitleUseCase("harry")

        coVerify { searchRepository.getSuggestion(SuggestionType.TITLE, "harry") }

        assert(result.isSuccess)

        assert(result.getOrNull() == listOfSuggestions)
    }

    @Test
    fun `test suggestTitleUseCase return failure`() = runTest {

        coEvery { searchRepository.getSuggestion(SuggestionType.TITLE, "harry") } returns
                Result.failure(Throwable("message"))

        val result = suggestTitleUseCase("harry")

        coVerify { searchRepository.getSuggestion(SuggestionType.TITLE, "harry") }

        assert(result.isFailure)

        assert(result.exceptionOrNull()?.message == "message")
    }
}