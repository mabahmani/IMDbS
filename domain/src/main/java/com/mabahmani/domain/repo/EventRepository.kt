package com.mabahmani.domain.repo

import com.mabahmani.domain.vo.EventDetails
import com.mabahmani.domain.vo.common.EventId

interface EventRepository {

    suspend fun getEventDetails(
        eventId: EventId,
        eventYear: Int,
    ): Result<EventDetails>
}