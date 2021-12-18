package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.*
import com.mabahmani.domain.vo.common.EventId
import com.mabahmani.domain.vo.common.NameId
import com.mabahmani.domain.vo.common.TitleId
import com.mabahmani.domain.vo.enum.SuggestionType
import javax.inject.Inject

class GetTitleDetailsUseCase @Inject constructor(private val titleRepository: TitleRepository){
    suspend operator fun invoke(titleId: TitleId) = titleRepository.getTitleDetails(titleId)
}