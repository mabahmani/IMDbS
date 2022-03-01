package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.VideoRepository
import com.mabahmani.domain.vo.common.VideoId
import javax.inject.Inject

class GetVideoDetailsUseCase @Inject constructor(private val videoRepository: VideoRepository){
    suspend operator fun invoke(videoId: VideoId) = videoRepository.getVideoDetails(videoId)
}