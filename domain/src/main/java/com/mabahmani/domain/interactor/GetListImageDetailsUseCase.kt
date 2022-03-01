package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.ImageRepository
import com.mabahmani.domain.vo.common.ImageId
import com.mabahmani.domain.vo.common.ListId
import javax.inject.Inject

class GetListImageDetailsUseCase @Inject constructor(private val imageRepository: ImageRepository) {
    suspend operator fun invoke(
        afterCursor: String? = null,
        beforeCursor: String? = null,
        numberOfFirstImages: Int? = null,
        numberOfLastImages: Int? = null,
        imageId: ImageId? = null,
        listId: ListId
    ) =
        imageRepository.getListImagesWithDetails(
            afterCursor, beforeCursor, numberOfFirstImages, numberOfLastImages, imageId, listId
        )
}