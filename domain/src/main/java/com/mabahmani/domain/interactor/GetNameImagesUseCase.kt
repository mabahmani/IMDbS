package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.ImageRepository
import com.mabahmani.domain.vo.common.NameId
import javax.inject.Inject

class GetNameImagesUseCase @Inject constructor(private val imageRepository: ImageRepository){
    suspend operator fun invoke(nameId: NameId) = imageRepository.getNameImages(nameId)
}