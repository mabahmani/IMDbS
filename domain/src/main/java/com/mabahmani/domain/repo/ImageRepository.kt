package com.mabahmani.domain.repo

import android.database.AbstractCursor
import com.mabahmani.domain.vo.ImageDetails
import com.mabahmani.domain.vo.common.*

interface ImageRepository {

    suspend fun getTitleImages(titleId: TitleId) : Result<List<Image>>

    suspend fun getNameImages(nameId: NameId) : Result<List<Image>>

    suspend fun getListImages(listId: ListId) : Result<List<Image>>

    suspend fun getTitleImagesWithDetails(
        afterCursor: String? = null,
        beforeCursor: String? = null,
        numberOfFirstImages: Int? = null,
        numberOfLastImages: Int? = null,
        imageId: ImageId? = null,
        titleId: TitleId
    ) : Result<List<ImageDetails>>

    suspend fun getNameImagesWithDetails(
        afterCursor: String? = null,
        beforeCursor: String? = null,
        numberOfFirstImages: Int? = null,
        numberOfLastImages: Int? = null,
        imageId: ImageId? = null,
        nameId: NameId
    ) : Result<List<ImageDetails>>

    suspend fun getListImagesWithDetails(
        afterCursor: String? = null,
        beforeCursor: String? = null,
        numberOfFirstImages: Int? = null,
        numberOfLastImages: Int? = null,
        imageId: ImageId? = null,
        listId: ListId
    ) : Result<List<ImageDetails>>
}