package com.mabahmani.data.repo

import com.mabahmani.data.ds.RemoteDataSourceImpl
import com.mabahmani.data.vo.generic.ApiResponse
import com.mabahmani.data.vo.res.*
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
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class TitleRepositoryImplTest {
    @MockK
    lateinit var remoteDataSourceImpl: RemoteDataSourceImpl

    @InjectMockKs
    lateinit var titleRepositoryImpl: TitleRepositoryImpl

    @RelaxedMockK
    lateinit var titleDetailsRes: TitleDetailsRes

    @RelaxedMockK
    lateinit var titleAwardsRes: TitleAwardsRes

    @RelaxedMockK
    lateinit var titleFullCreditsRes: TitleFullCreditsRes

    @RelaxedMockK
    lateinit var titleParentsGuideRes: TitleParentsGuideRes

    @RelaxedMockK
    lateinit var titleTechnicalSpecsRes: TitleTechnicalSpecsRes

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun getTitleDetails() = runTest {
        coEvery { remoteDataSourceImpl.getTitleDetails(any()) } returns
                Result.success(
                    ApiResponse(titleDetailsRes, true, "")
                )

        val result = titleRepositoryImpl.getTitleDetails(TitleId("tt000000"))

        coVerify { remoteDataSourceImpl.getTitleDetails("tt000000") }

        assert(result.isSuccess)
    }

    @Test
    fun getTitleAwards() = runTest {
        coEvery { remoteDataSourceImpl.getTitleDetails(any()) } returns
                Result.success(
                    ApiResponse(titleDetailsRes, true, "")
                )

        val result = titleRepositoryImpl.getTitleDetails(TitleId("tt000000"))

        coVerify { remoteDataSourceImpl.getTitleDetails("tt000000") }

        assert(result.isSuccess)
    }

    @Test
    fun getTitleFullCasts()  = runTest {
        coEvery { remoteDataSourceImpl.getTitleFullCredits(any()) } returns
                Result.success(
                    ApiResponse(titleFullCreditsRes, true, "")
                )

        val result = titleRepositoryImpl.getTitleFullCasts(TitleId("tt000000"))

        coVerify { remoteDataSourceImpl.getTitleFullCredits("tt000000") }

        assert(result.isSuccess)
    }
    @Test
    fun getTitleParentsGuide()  = runTest {
        coEvery { remoteDataSourceImpl.getTitleParentalGuide(any()) } returns
                Result.success(
                    ApiResponse(titleParentsGuideRes, true, "")
                )

        val result = titleRepositoryImpl.getTitleParentsGuide(TitleId("tt000000"))

        coVerify { remoteDataSourceImpl.getTitleParentalGuide("tt000000") }

        assert(result.isSuccess)
    }
    @Test
    fun getTitleTechnicalSpecs() = runTest {
        coEvery { remoteDataSourceImpl.getTitleTechnicalSpecs(any()) } returns
                Result.success(
                    ApiResponse(titleTechnicalSpecsRes, true, "")
                )

        val result = titleRepositoryImpl.getTitleTechnicalSpecs(TitleId("tt000000"))

        coVerify { remoteDataSourceImpl.getTitleTechnicalSpecs("tt000000") }

        assert(result.isSuccess)
    }
}