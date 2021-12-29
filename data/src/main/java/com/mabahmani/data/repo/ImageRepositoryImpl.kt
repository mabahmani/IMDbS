package com.mabahmani.data.repo

import com.mabahmani.data.ds.RemoteDataSource
import com.mabahmani.domain.repo.ImageRepository
import com.mabahmani.domain.vo.ImageDetails
import com.mabahmani.domain.vo.common.*
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) : ImageRepository {

    override suspend fun getTitleImages(titleId: TitleId, page: String): Result<List<ImageLink>> {
        val remoteResult = remoteDataSource.getTitleImages(titleId.value, page)

        return if (remoteResult.isSuccess) {
            try {
                Result.success(remoteResult.getOrNull()!!.data.toImages())
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull() ?: java.lang.Exception())
    }

    override suspend fun getNameImages(nameId: NameId, page: String): Result<List<ImageLink>> {
        val remoteResult = remoteDataSource.getNameImages(nameId.value, page)

        return if (remoteResult.isSuccess) {
            try {
                Result.success(remoteResult.getOrNull()!!.data.toImages())
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull() ?: java.lang.Exception())
    }

    override suspend fun getListImages(listId: ListId): Result<List<ImageLink>> {
        val remoteResult = remoteDataSource.getListImages(listId.value)

        return if (remoteResult.isSuccess) {
            try {
                Result.success(remoteResult.getOrNull()!!.data.toImages())
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull() ?: java.lang.Exception())
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