package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.*
import com.mabahmani.domain.vo.common.EventId
import com.mabahmani.domain.vo.common.ListId
import com.mabahmani.domain.vo.common.TitleId
import com.mabahmani.domain.vo.enum.SuggestionType
import javax.inject.Inject

class GetListImagesUseCase @Inject constructor(private val imageRepository: ImageRepository){
    suspend operator fun invoke(listId: ListId) = imageRepository.getListImages(listId)
}