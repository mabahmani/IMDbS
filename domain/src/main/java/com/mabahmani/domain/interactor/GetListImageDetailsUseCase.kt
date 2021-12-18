package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.*
import com.mabahmani.domain.vo.common.EventId
import com.mabahmani.domain.vo.common.ImageId
import com.mabahmani.domain.vo.common.ListId
import com.mabahmani.domain.vo.common.TitleId
import com.mabahmani.domain.vo.enum.SuggestionType
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