package com.mabahmani.data.repo

import com.mabahmani.data.ds.RemoteDataSourceImpl
import com.mabahmani.data.vo.generic.ApiResponse
import com.mabahmani.data.vo.res.ImageDetailsRes
import com.mabahmani.data.vo.res.ImagesRes
import com.mabahmani.domain.vo.common.ImageId
import com.mabahmani.domain.vo.common.ListId
import com.mabahmani.domain.vo.common.NameId
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
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ImageRepositoryImplTest {
    @MockK
    lateinit var remoteDataSourceImpl: RemoteDataSourceImpl

    @InjectMockKs
    lateinit var imageRepositoryImpl: ImageRepositoryImpl

    @RelaxedMockK
    lateinit var imagesRes: ImagesRes

    @RelaxedMockK
    lateinit var imageDetailsRes: ImageDetailsRes

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun getTitleImages() = runTest {
        coEvery { remoteDataSourceImpl.getTitleImages(any(), any()) } returns
                Result.success(
                    ApiResponse(imagesRes, true, "")
                )

        val result = imageRepositoryImpl.getTitleImages(TitleId("tt000000"), "1")

        coVerify { remoteDataSourceImpl.getTitleImages("tt000000", "1") }

        assert(result.isSuccess)
    }

    @Test
    fun getNameImages() = runTest {
        coEvery { remoteDataSourceImpl.getNameImages(any(), any()) } returns
                Result.success(
                    ApiResponse(imagesRes, true, "")
                )

        val result = imageRepositoryImpl.getNameImages(NameId("nm000000"), "1")

        coVerify { remoteDataSourceImpl.getNameImages("nm000000", "1") }

        assert(result.isSuccess)
    }

    @Test
    fun getListImages() = runTest {
        coEvery { remoteDataSourceImpl.getListImages(any()) } returns
                Result.success(
                    ApiResponse(imagesRes, true, "")
                )

        val result = imageRepositoryImpl.getListImages(ListId("li000000"))

        coVerify { remoteDataSourceImpl.getListImages("li000000") }

        assert(result.isSuccess)
    }

    @Test
    fun getTitleImagesWithDetails() = runTest {
        coEvery { remoteDataSourceImpl.getTitleImagesWithDetails(any(),any(),any(),any(),any(),any()) } returns
                Result.success(
                    ApiResponse(imageDetailsRes, true, "")
                )

        val result = imageRepositoryImpl.getTitleImagesWithDetails("after", "before", 5, 6, ImageId("rm000000"), TitleId("tt000000"))

        coVerify { remoteDataSourceImpl.getTitleImagesWithDetails("tt000000", "after", "before", 5, 6, "rm000000") }

        assert(result.isSuccess)
    }

    @Test
    fun getNameImagesWithDetails() = runTest {
        coEvery { remoteDataSourceImpl.getNameImagesWithDetails(any(),any(),any(),any(),any(),any()) } returns
                Result.success(
                    ApiResponse(imageDetailsRes, true, "")
                )

        val result = imageRepositoryImpl.getNameImagesWithDetails("after", "before", 5, 6, ImageId("rm000000"), NameId("nm000000"))

        coVerify { remoteDataSourceImpl.getNameImagesWithDetails("nm000000", "after", "before", 5, 6, "rm000000") }

        assert(result.isSuccess)
    }

    @Test
    fun getListImagesWithDetails() = runTest {
        coEvery { remoteDataSourceImpl.getListImagesWithDetails(any(),any(),any(),any(),any(),any()) } returns
                Result.success(
                    ApiResponse(imageDetailsRes, true, "")
                )

        val result = imageRepositoryImpl.getListImagesWithDetails("after", "before", 5, 6, ImageId("rm000000"), ListId("li000000"))

        coVerify { remoteDataSourceImpl.getListImagesWithDetails("li000000", "after", "before", 5, 6, "rm000000") }

        assert(result.isSuccess)
    }
}