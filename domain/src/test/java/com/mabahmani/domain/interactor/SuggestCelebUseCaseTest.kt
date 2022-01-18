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
class SuggestCelebUseCaseTest {

    @MockK
    lateinit var searchRepository: SearchRepository

    @InjectMockKs
    lateinit var suggestCelebUseCase: SuggestCelebUseCase

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
    fun `test suggestCelebUseCase return success`() = runTest{

        coEvery { searchRepository.getSuggestion(SuggestionType.CELEB, "harry") } returns
                Result.success(listOfSuggestions)

        val result = suggestCelebUseCase("harry")

        coVerify { searchRepository.getSuggestion(SuggestionType.CELEB, "harry") }

        assert(result.isSuccess)

        assert(result.getOrNull() == listOfSuggestions)
    }

    @Test
    fun `test suggestCelebUseCase return failure`() = runTest {

        coEvery { searchRepository.getSuggestion(SuggestionType.CELEB, "harry") } returns
                Result.failure(Throwable("message"))

        val result = suggestCelebUseCase("harry")

        coVerify { searchRepository.getSuggestion(SuggestionType.CELEB, "harry") }

        assert(result.isFailure)

        assert(result.exceptionOrNull()?.message == "message")
    }
}