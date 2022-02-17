package com.mabahmani.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.mabahmani.data.ds.RemoteDataSource
import com.mabahmani.data.ps.ListImagesPagingSource
import com.mabahmani.data.ps.NameImagesPagingSource
import com.mabahmani.data.ps.TitleImagesPagingSource
import com.mabahmani.domain.repo.ImageRepository
import com.mabahmani.domain.vo.ImageDetails
import com.mabahmani.domain.vo.common.*
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) : ImageRepository {

    override suspend fun getTitleImages(titleId: TitleId): Pager<Int, ImageLink> {
        return Pager(PagingConfig(50)){
            TitleImagesPagingSource(
                remoteDataSource,
                titleId
            )
        }
    }

    override suspend fun getNameImages(nameId: NameId): Pager<Int, ImageLink>  {

        return Pager(PagingConfig(50)){
            NameImagesPagingSource(
                remoteDataSource,
                nameId
            )
        }
    }

    override suspend fun getListImages(listId: ListId): Pager<Int, ImageLink>{

        return Pager(PagingConfig(50)){
            ListImagesPagingSource(
                remoteDataSource,
                listId
            )
        }
    }

    override suspend fun getTitleImagesWithDetails(
        afterCursor: String?,
        beforeCursor: String?,
        numberOfFirstImages: Int?,
        numberOfLastImages: Int?,
        imageId: ImageId?,
        titleId: TitleId
    ): Result<ImageDetails> {
        val remoteResult = remoteDataSource.getTitleImagesWithDetails(
            titleId.value,
            afterCursor,
            beforeCursor,
            numberOfFirstImages,
            numberOfLastImages,
            imageId?.value
        )

        return if (remoteResult.isSuccess) {
            try {
                Result.success(remoteResult.getOrNull()!!.data.toImageDetails())
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull() ?: java.lang.Exception())
    }

    override suspend fun getNameImagesWithDetails(
        afterCursor: String?,
        beforeCursor: String?,
        numberOfFirstImages: Int?,
        numberOfLastImages: Int?,
        imageId: ImageId?,
        nameId: NameId
    ): Result<ImageDetails> {
        val remoteResult = remoteDataSource.getNameImagesWithDetails(
            nameId.value,
            afterCursor,
            beforeCursor,
            numberOfFirstImages,
            numberOfLastImages,
            imageId?.value
        )

        return if (remoteResult.isSuccess) {
            try {
                Result.success(remoteResult.getOrNull()!!.data.toImageDetails())
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull() ?: java.lang.Exception())
    }

    override suspend fun getListImagesWithDetails(
        afterCursor: String?,
        beforeCursor: String?,
        numberOfFirstImages: Int?,
        numberOfLastImages: Int?,
        imageId: ImageId?,
        listId: ListId
    ): Result<ImageDetails> {
        val remoteResult = remoteDataSource.getListImagesWithDetails(
            listId.value,
            afterCursor,
            beforeCursor,
            numberOfFirstImages,
            numberOfLastImages,
            imageId?.value
        )

        return if (remoteResult.isSuccess) {
            try {
                Result.success(remoteResult.getOrNull()!!.data.toImageDetails())
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull() ?: java.lang.Exception())
    }
}