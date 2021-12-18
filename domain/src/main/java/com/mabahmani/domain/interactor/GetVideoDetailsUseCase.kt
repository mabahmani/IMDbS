package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.*
import com.mabahmani.domain.vo.common.EventId
import com.mabahmani.domain.vo.common.TitleId
import com.mabahmani.domain.vo.common.VideoId
import com.mabahmani.domain.vo.enum.SuggestionType
import javax.inject.Inject

class GetVideoDetailsUseCase @Inject constructor(private val videoRepository: VideoRepository){
    suspend operator fun invoke(videoId: VideoId) = videoRepository.getVideoDetails(videoId)
}