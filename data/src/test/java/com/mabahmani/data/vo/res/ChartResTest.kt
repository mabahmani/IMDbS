package com.mabahmani.data.vo.res

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ChartResTest{
    private lateinit var model: ChartRes

    @Before
    fun setup(){
        model = ChartRes(
            "https://image.com/item.jpg",
            5.6f,
            "",
            8200,
            4,
            "movie name",
            "tt996666",
            "1997"
        )
    }

    @Test
    fun `test ChartRes model to Title domain model  conversion`(){
        val result = model.toTitle()
        assertEquals(null, result.releaseDay)
        assertEquals(null, result.releaseMonth)
        assertEquals("1997", result.releaseYear)
        assertEquals(null, result.certificate)
        assertEquals("https://image.com/item.jpg", result.cover?.value)
        assertEquals(null, result.directors)
        assertEquals(null, result.genres)
        assertEquals(null, result.gross)
        assertEquals("5.6", result.rate)
        assertEquals(null, result.runtime)
        assertEquals(null, result.stars)
        assertEquals(null, result.summary)
        assertEquals("movie name", result.title)
        assertEquals("tt996666", result.titleId?.value)
        assertEquals("8200", result.voteCount)
        assertEquals(null, result.videoId)
        assertEquals(null, result.videoName)
        assertEquals(null, result.videoPreview)
        assertEquals(null, result.videoRuntime)
    }

}