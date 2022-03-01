package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.ImageRepository
import com.mabahmani.domain.vo.common.ImageId
import com.mabahmani.domain.vo.common.TitleId
import javax.inject.Inject

class GetTitleImageDetailsUseCase @Inject constructor(private val imageRepository: ImageRepository) {
    suspend operator fun invoke(
        afterCursor: String? = null,
        beforeCursor: String? = null,
        numberOfFirstImages: Int? = null,
        numberOfLastImages: Int? = null,
        imageId: ImageId? = null,
        titleId: TitleId
    ) =
        imageRepository.getTitleImagesWithDetails(
            afterCursor, beforeCursor, numberOfFirstImages, numberOfLastImages, imageId, titleId
        )
}