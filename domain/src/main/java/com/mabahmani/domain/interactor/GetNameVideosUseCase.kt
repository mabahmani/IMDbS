package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.VideoRepository
import com.mabahmani.domain.vo.common.NameId
import javax.inject.Inject

class GetNameVideosUseCase @Inject constructor(private val videoRepository: VideoRepository){
    suspend operator fun invoke(nameId: NameId) = videoRepository.getNameVideos(nameId)
}