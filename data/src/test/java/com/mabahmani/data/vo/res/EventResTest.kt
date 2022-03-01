package com.mabahmani.data.vo.res

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class EventResTest{
    private lateinit var model: EventRes

    @Before
    fun setup(){
        model = EventRes(
            "event name",
            "ev568999"
        )
    }

    @Test
    fun `test EventRes model to Event domain model  conversion`(){
        val result = model.toEvent()
        assertEquals("event name", result.name)
        assertEquals("ev568999", result.eventId.value)
    }

}