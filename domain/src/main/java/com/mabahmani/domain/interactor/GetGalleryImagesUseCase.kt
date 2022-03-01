package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.ImageRepository
import com.mabahmani.domain.vo.common.GalleryId
import javax.inject.Inject

class GetGalleryImagesUseCase @Inject constructor(private val imageRepository: ImageRepository){
    suspend operator fun invoke(galleryId: GalleryId) = imageRepository.getGalleryImages(galleryId)
}