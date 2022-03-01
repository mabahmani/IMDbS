package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.EventRepository
import com.mabahmani.domain.vo.common.EventId
import javax.inject.Inject

class GetEventDetailsUseCase @Inject constructor(private val eventRepository: EventRepository){
    suspend operator fun invoke(eventId: EventId, eventYear: Int) = eventRepository.getEventDetails(eventId, eventYear)
}