package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.*
import com.mabahmani.domain.vo.common.EventId
import com.mabahmani.domain.vo.common.ImageId
import com.mabahmani.domain.vo.common.NameId
import com.mabahmani.domain.vo.common.TitleId
import com.mabahmani.domain.vo.enum.SuggestionType
import javax.inject.Inject

class GetNameImageDetailsUseCase @Inject constructor(private val imageRepository: ImageRepository) {
    suspend operator fun invoke(
        afterCursor: String? = null,
        beforeCursor: String? = null,
        numberOfFirstImages: Int? = null,
        numberOfLastImages: Int? = null,
        imageId: ImageId? = null,
        nameId: NameId
    ) =
        imageRepository.getNameImagesWithDetails(
            afterCursor, beforeCursor, numberOfFirstImages, numberOfLastImages, imageId, nameId
        )
}