package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.NameRepository
import com.mabahmani.domain.vo.common.NameId
import javax.inject.Inject

class GetNameBioUseCase @Inject constructor(private val nameRepository: NameRepository){
    suspend operator fun invoke(nameId: NameId) = nameRepository.getNameBio(nameId)
}