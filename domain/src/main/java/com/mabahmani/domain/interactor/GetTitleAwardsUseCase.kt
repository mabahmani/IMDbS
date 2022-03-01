package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.TitleRepository
import com.mabahmani.domain.vo.common.TitleId
import javax.inject.Inject

class GetTitleAwardsUseCase @Inject constructor(private val titleRepository: TitleRepository){
    suspend operator fun invoke(titleId: TitleId) = titleRepository.getTitleAwards(titleId)
}