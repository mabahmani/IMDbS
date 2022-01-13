package com.mabahmani.data.vo.res

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CalenderResTest{
    private lateinit var model: CalenderRes

    @Before
    fun setup(){
        model = CalenderRes(
            "2021-05-12",
            listOf(
                CalenderRes.Title(
                    "tt568999",
                    "movie name"
                )
            )
        )
    }

    @Test
    fun `test CalenderRes model to Calender domain model  conversion`(){
        val result = model.toCalender()
        assertEquals("2021-05-12", result.date)
        assertEquals("movie name", result.titles[0].title)
        assertEquals("tt568999", result.titles[0].titleId.value)
    }

}