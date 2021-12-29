package com.mabahmani.domain.repo

import com.mabahmani.domain.vo.ImageDetails
import com.mabahmani.domain.vo.common.*

interface ImageRepository {

    suspend fun getTitleImages(titleId: TitleId, page: String) : Result<List<ImageLink>>

    suspend fun getNameImages(nameId: NameId, page: String) : Result<List<ImageLink>>

    suspend fun getListImages(listId: ListId) : Result<List<ImageLink>>

    suspend fun getTitleImagesWithDetails(
        afterCursor: String? = null,
        beforeCursor: String? = null,
        numberOfFirstImages: Int? = null,
        numberOfLastImages: Int? = null,
        imageId: ImageId? = null,
        titleId: TitleId
    ) : Result<ImageDetails>

    suspend fun getNameImagesWithDetails(
        afterCursor: String? = null,
        beforeCursor: String? = null,
        numberOfFirstImages: Int? = null,
        numberOfLastImages: Int? = null,
        imageId: ImageId? = null,
        nameId: NameId
    ) : Result<ImageDetails>

    suspend fun getListImagesWithDetails(
        afterCursor: String? = null,
        beforeCursor: String? = null,
        numberOfFirstImages: Int? = null,
        numberOfLastImages: Int? = null,
        imageId: ImageId? = null,
        listId: ListId
    ) : Result<ImageDetails>
}