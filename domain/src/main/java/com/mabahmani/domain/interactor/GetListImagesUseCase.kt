package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.ImageRepository
import com.mabahmani.domain.vo.common.ListId
import javax.inject.Inject

class GetListImagesUseCase @Inject constructor(private val imageRepository: ImageRepository){
    suspend operator fun invoke(listId: ListId) = imageRepository.getListImages(listId)
}