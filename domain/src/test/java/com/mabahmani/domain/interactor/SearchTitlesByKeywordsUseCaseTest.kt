package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.SearchRepository
import com.mabahmani.domain.vo.common.Title
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
class SearchTitlesByKeywordsUseCaseTest {

    @MockK
    lateinit var searchRepository: SearchRepository

    @InjectMockKs
    lateinit var searchTitlesByKeywordsUseCase: SearchTitlesByKeywordsUseCase

    @RelaxedMockK
    lateinit var listOfTitles: List<Title>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test searchTitlesByKeywordsUseCase return success`() = runTest{

        coEvery { searchRepository.searchTitles(keywords = mutableListOf("superhero")) } returns
                Result.success(listOfTitles)

        val result = searchTitlesByKeywordsUseCase("superhero")

        coVerify { searchRepository.searchTitles(keywords = mutableListOf("superhero")) }

        assert(result.isSuccess)

        assert(result.getOrNull() == listOfTitles)
    }

    @Test
    fun `test searchTitlesByKeywordsUseCase return failure`() = runTest {

        coEvery { searchRepository.searchTitles(keywords = mutableListOf("superhero")) } returns
                Result.failure(Throwable("message"))

        val result = searchTitlesByKeywordsUseCase("superhero")

        coVerify {searchRepository.searchTitles(keywords = mutableListOf("superhero")) }

        assert(result.isFailure)

        assert(result.exceptionOrNull()?.message == "message")
    }
}