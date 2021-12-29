package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.*
import com.mabahmani.domain.vo.common.TitleId
import javax.inject.Inject

class GetTitleImagesUseCase @Inject constructor(private val imageRepository: ImageRepository){
    suspend operator fun invoke(titleId: TitleId, page: String) = imageRepository.getTitleImages(titleId, page)
}