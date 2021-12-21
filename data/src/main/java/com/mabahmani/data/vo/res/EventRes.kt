package com.mabahmani.data.vo.res

import com.mabahmani.domain.vo.common.Event
import com.mabahmani.domain.vo.common.EventId

data class EventRes(
    val event: String,
    val eventId: String
){
    fun toEvent(): Event{
        return Event(event, EventId(eventId))
    }
}