package com.mabahmani.data.repo

import com.mabahmani.data.ds.RemoteDataSourceImpl
import com.mabahmani.data.vo.generic.ApiResponse
import com.mabahmani.data.vo.res.HomeExtraRes
import com.mabahmani.data.vo.res.HomeRes
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
class HomeRepositoryImplTest {
    @MockK
    lateinit var remoteDataSourceImpl: RemoteDataSourceImpl

    @InjectMockKs
    lateinit var homeRepositoryImpl: HomeRepositoryImpl

    @RelaxedMockK
    lateinit var homeRes: HomeRes

    @RelaxedMockK
    lateinit var homeExtraRes: HomeExtraRes

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun getHome() = runTest {
        coEvery { remoteDataSourceImpl.getHome() } returns
                Result.success(
                    ApiResponse(homeRes, true, "")
                )

        val result = homeRepositoryImpl.getHome()

        coVerify { remoteDataSourceImpl.getHome() }

        assert(result.isSuccess)
    }

    @Test
    fun getHomeExtra()  = runTest {
        coEvery { remoteDataSourceImpl.getHomeExtra() } returns
                Result.success(
                    ApiResponse(homeExtraRes, true, "")
                )

        val result = homeRepositoryImpl.getHomeExtra()

        coVerify { remoteDataSourceImpl.getHomeExtra() }

        assert(result.isSuccess)
    }
}