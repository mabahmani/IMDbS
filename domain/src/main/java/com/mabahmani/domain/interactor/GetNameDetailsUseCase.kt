package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.NameRepository
import com.mabahmani.domain.vo.common.NameId
import javax.inject.Inject

class GetNameDetailsUseCase @Inject constructor(private val nameRepository: NameRepository){
    suspend operator fun invoke(nameId: NameId) = nameRepository.getNameDetails(nameId)
}