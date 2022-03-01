package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.VideoRepository
import com.mabahmani.domain.vo.common.TitleId
import javax.inject.Inject

class GetTitleVideosUseCase @Inject constructor(private val videoRepository: VideoRepository){
    suspend operator fun invoke(titleId: TitleId) = videoRepository.getTitleVideos(titleId)
}