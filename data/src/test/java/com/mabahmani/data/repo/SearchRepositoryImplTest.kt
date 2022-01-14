package com.mabahmani.data.repo

import com.mabahmani.data.ds.RemoteDataSourceImpl
import com.mabahmani.data.vo.generic.ApiResponse
import com.mabahmani.data.vo.res.SuggestRes
import com.mabahmani.domain.vo.common.NameId
import com.mabahmani.domain.vo.common.NewsId
import com.mabahmani.domain.vo.common.TitleId
import com.mabahmani.domain.vo.enum.*
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchRepositoryImplTest {
    @MockK
    lateinit var remoteDataSourceImpl: RemoteDataSourceImpl

    @InjectMockKs
    lateinit var searchRepositoryImpl: SearchRepositoryImpl

    @RelaxedMockK
    lateinit var suggestRes: SuggestRes

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun getGenres() = runTest {
        coEvery { remoteDataSourceImpl.getGenres() } returns
                Result.success(
                    ApiResponse(listOf(), true, "")
                )

        val result = searchRepositoryImpl.getGenres()

        coVerify { remoteDataSourceImpl.getGenres() }

        assert(result.isSuccess)
    }

    @Test
    fun getKeywords() = runTest {
        coEvery { remoteDataSourceImpl.getKeywords() } returns
                Result.success(
                    ApiResponse(listOf(), true, "")
                )

        val result = searchRepositoryImpl.getKeywords()

        coVerify { remoteDataSourceImpl.getKeywords() }

        assert(result.isSuccess)
    }

    @Test
    fun getEvents() = runTest {
        coEvery { remoteDataSourceImpl.getEvents() } returns
                Result.success(
                    ApiResponse(listOf(), true, "")
                )

        val result = searchRepositoryImpl.getEvents()

        coVerify { remoteDataSourceImpl.getEvents() }

        assert(result.isSuccess)
    }

    @Test
    fun getCalender() = runTest {
        coEvery { remoteDataSourceImpl.getTitlesCalender() } returns
                Result.success(
                    ApiResponse(listOf(), true, "")
                )

        val result = searchRepositoryImpl.getCalender()

        coVerify { remoteDataSourceImpl.getTitlesCalender() }

        assert(result.isSuccess)
    }

    @Test
    fun searchNames() = runTest {
        coEvery { remoteDataSourceImpl.searchNames(any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any()) } returns
                Result.success(
                    ApiResponse(listOf(), true, "")
                )

        val result = searchRepositoryImpl.searchNames("bio","1999","08-05",
            listOf("iran"),"2050", listOf("iran"), listOf(Gender.MALE), listOf(NameGroup.BEST_DIRECTOR_WINNER),"farhadi", TitleId("tt000000"),NameSort.DEATH_DATE_DESC,
            listOf(NameSign.AQUARIUS),1)

        coVerify { remoteDataSourceImpl.searchNames("bio","1999","08-05","iran","2050","iran","MALE","BEST_DIRECTOR_WINNER","farhadi","tt000000","death_date,desc","AQUARIUS","1") }

        assert(result.isSuccess)
    }

    @Test
    fun searchTitles() = runTest {
        coEvery { remoteDataSourceImpl.searchTitles(any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any()) } returns
                Result.success(
                    ApiResponse(listOf(), true, "")
                )

        val result = searchRepositoryImpl.searchTitles(
            listOf(USCertificate.USAG, USCertificate.USANC_17),
            listOf(Color.ACES, Color.BLACK_AND_WHITE),
            listOf(Company.DISNEY, Company.DREAMWORKS, Company.FOX),
            listOf("iran", "canada"),
            listOf("action"),
            listOf(TitleGroup.BEST_DIRECTOR_WINNER),
            listOf("superhero"),
            listOf("fa", "en"),
            listOf("iran", "france"),
            "plot",
            "1999-05-10",
            NameId("nm000000"),
            "1",
            TitleSort.RELEASE_DATE_ASC,
            5,
            "eli",
            listOf(TitleType.DOCUMENTARY),
            "5.6-8.9")

        coVerify { remoteDataSourceImpl.searchTitles("USAG, USANC_17","ACES, BLACK_AND_WHITE","DISNEY, DREAMWORKS, FOX","iran, canada","action","BEST_DIRECTOR_WINNER","superhero","fa, en","iran, france","plot","1999-05-10","nm000000","1","release_date,asc","5","eli", "DOCUMENTARY","5.6-8.9") }

        assert(result.isSuccess)
    }

    @Test
    fun getSuggestion() = runTest {
        coEvery { remoteDataSourceImpl.suggestAll(any(),any()) } returns
                Result.success(
                    ApiResponse(suggestRes, true, "")
                )

        coEvery { remoteDataSourceImpl.suggestNames(any(),any()) } returns
                Result.success(
                    ApiResponse(suggestRes, true, "")
                )

        coEvery { remoteDataSourceImpl.suggestTitles(any(),any()) } returns
                Result.success(
                    ApiResponse(suggestRes, true, "")
                )

        val result = searchRepositoryImpl.getSuggestion(SuggestionType.TITLE, "term")

        coVerify { remoteDataSourceImpl.suggestTitles("t", "term") }

        assert(result.isSuccess)
    }
}