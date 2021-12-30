package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.*
import com.mabahmani.domain.vo.common.EventId
import com.mabahmani.domain.vo.common.NameId
import com.mabahmani.domain.vo.common.TitleId
import com.mabahmani.domain.vo.enum.SuggestionType
import javax.inject.Inject

class GetNameImagesUseCase @Inject constructor(private val imageRepository: ImageRepository){
    suspend operator fun invoke(nameId: NameId, page: String) = imageRepository.getNameImages(nameId, page)
}