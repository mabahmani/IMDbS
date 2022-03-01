package com.mabahmani.data.vo.res

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ChartBoxOfficeResTest{
    private lateinit var model: ChartBoxOfficeRes

    @Before
    fun setup(){
        model = ChartBoxOfficeRes(
            "2021-05-12",
            listOf(
                ChartBoxOfficeRes.BoxOfficeTitle(
                    "tt69559",
                    "movie name",
                    "",
                    "560,000",
                    "4,560,000",
                    "6"
                )
            )
        )
    }

    @Test
    fun `test ChartBoxOfficeRes model to BoxOffice domain model  conversion`(){
        val result = model.toBoxOffice()
        assertEquals("2021-05-12", result.startDate)
        assertEquals("2021-05-12", result.endDate)
        assertEquals("6", result.boxOfficeItems?.get(0)?.weeks)
        assertEquals("560,000", result.boxOfficeItems?.get(0)?.weekendGross)
        assertEquals("4,560,000", result.boxOfficeItems?.get(0)?.gross)
        assertEquals(null, result.boxOfficeItems?.get(0)?.image?.value)
        assertEquals("tt69559", result.boxOfficeItems?.get(0)?.titleId?.value)
        assertEquals("movie name", result.boxOfficeItems?.get(0)?.title)
    }

}