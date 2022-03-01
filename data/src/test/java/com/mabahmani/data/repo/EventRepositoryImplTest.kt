package com.mabahmani.data.repo

import com.mabahmani.data.ds.RemoteDataSourceImpl
import com.mabahmani.data.vo.generic.ApiResponse
import com.mabahmani.data.vo.res.EventDetailsRes
import com.mabahmani.domain.vo.common.EventId
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
class EventRepositoryImplTest {
    @MockK
    lateinit var remoteDataSourceImpl: RemoteDataSourceImpl

    @InjectMockKs
    lateinit var eventRepositoryImpl: EventRepositoryImpl

    @RelaxedMockK
    lateinit var eventDetailsRes: EventDetailsRes

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun getEventDetails() = runTest {
        coEvery { remoteDataSourceImpl.getEventDetails(any(), any()) } returns
                Result.success(
                    ApiResponse(eventDetailsRes, true, "")
                )

        val result = eventRepositoryImpl.getEventDetails(EventId("ev000000"),2000)

        coVerify { remoteDataSourceImpl.getEventDetails("ev000000","2000") }

        assert(result.isSuccess)
    }
}