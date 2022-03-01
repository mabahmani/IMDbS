package com.mabahmani.data.repo

import com.mabahmani.data.ds.RemoteDataSourceImpl
import com.mabahmani.data.vo.generic.ApiResponse
import com.mabahmani.data.vo.res.NewsDetailsRes
import com.mabahmani.domain.vo.common.NewsId
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
class NewsRepositoryImplTest {
    @MockK
    lateinit var remoteDataSourceImpl: RemoteDataSourceImpl

    @InjectMockKs
    lateinit var newsRepositoryImpl: NewsRepositoryImpl

    @RelaxedMockK
    lateinit var newsDetailsRes: NewsDetailsRes

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun getNewsDetails() = runTest {
        coEvery { remoteDataSourceImpl.getNewsDetails(any()) } returns
                Result.success(
                    ApiResponse(newsDetailsRes, true, "")
                )

        val result = newsRepositoryImpl.getNewsDetails(NewsId("ni000000"))

        coVerify { remoteDataSourceImpl.getNewsDetails("ni000000") }

        assert(result.isSuccess)
    }
}