package com.mabahmani.data.vo.res

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BoxOfficeResTest{
    private lateinit var model: BoxOfficeRes

    @Before
    fun setup(){
        model = BoxOfficeRes(
            listOf(BoxOfficeRes.BoxOfficeTitle("","5,000,000","movie name", "tt00115566","1,000,000","5")),
            "21 December - 28 December"
        )
    }

    @Test
    fun `test boxOfficeRes model to boxOffice domain model  conversion`(){
        val boxOffice = model.toBoxOffice()
        assertEquals("21 December - 28 December", boxOffice.startDate)
        assertEquals("21 December - 28 December", boxOffice.endDate )
        assertEquals("", boxOffice.boxOfficeItems?.get(0)?.image?.value)
        assertEquals("tt00115566", boxOffice.boxOfficeItems?.get(0)?.titleId?.value)
        assertEquals("5,000,000", boxOffice.boxOfficeItems?.get(0)?.gross)
        assertEquals("movie name", boxOffice.boxOfficeItems?.get(0)?.title)
        assertEquals("1,000,000", boxOffice.boxOfficeItems?.get(0)?.weekendGross)
        assertEquals("5", boxOffice.boxOfficeItems?.get(0)?.weeks)
    }

}