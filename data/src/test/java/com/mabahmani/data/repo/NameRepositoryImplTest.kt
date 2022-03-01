package com.mabahmani.data.repo

import com.mabahmani.data.ds.RemoteDataSourceImpl
import com.mabahmani.data.vo.generic.ApiResponse
import com.mabahmani.data.vo.res.NameAwardsRes
import com.mabahmani.data.vo.res.NameBioRes
import com.mabahmani.data.vo.res.NameDetailsRes
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
class NameRepositoryImplTest {
    @MockK
    lateinit var remoteDataSourceImpl: RemoteDataSourceImpl

    @InjectMockKs
    lateinit var nameRepositoryImpl: NameRepositoryImpl

    @RelaxedMockK
    lateinit var nameDetailsRes: NameDetailsRes

    @RelaxedMockK
    lateinit var nameAwardsRes: NameAwardsRes

    @RelaxedMockK
    lateinit var nameBioRes: NameBioRes

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun getNameDetails() = runTest {
        coEvery { remoteDataSourceImpl.getNameDetails(any()) } returns
                Result.success(
                    ApiResponse(nameDetailsRes, true, "")
                )

        val result = nameRepositoryImpl.getNameDetails(NameId("nm000000"))

        coVerify { remoteDataSourceImpl.getNameDetails("nm000000") }

        assert(result.isSuccess)
    }

    @Test
    fun getNameAwards() = runTest {
        coEvery { remoteDataSourceImpl.getNameAwards(any()) } returns
                Result.success(
                    ApiResponse(nameAwardsRes, true, "")
                )

        val result = nameRepositoryImpl.getNameAwards(NameId("nm000000"))

        coVerify { remoteDataSourceImpl.getNameAwards("nm000000") }

        assert(result.isSuccess)
    }


    @Test
    fun getNameBio() = runTest {
        coEvery { remoteDataSourceImpl.getNameBio(any()) } returns
                Result.success(
                    ApiResponse(nameBioRes, true, "")
                )

        val result = nameRepositoryImpl.getNameBio(NameId("nm000000"))

        coVerify { remoteDataSourceImpl.getNameBio("nm000000") }

        assert(result.isSuccess)
    }
}