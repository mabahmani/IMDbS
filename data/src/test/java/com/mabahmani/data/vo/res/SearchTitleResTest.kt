package com.mabahmani.data.vo.res

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SearchTitleResTest {
    private lateinit var model: SearchTitlesRes

    @Before
    fun setup() {
        model = SearchTitlesRes(
            "certificate",
            "cover",
            listOf(SearchTitlesRes.Director("dirName", "nm845665566")),
            "genres",
            "8.9",
            "59878",
            "0",
            "120",
            listOf(SearchTitlesRes.Star("starName", "nm568798")),
            "summary",
            "title",
            "tt568793",
            "2003"
        )
    }

    @Test
    fun `test SearchTitlesRes model to Title domain model  conversion`() {
        val result = model.toTitle()

        assertEquals(null, result.releaseDay)
        assertEquals(null, result.releaseMonth)
        assertEquals("2003", result.releaseYear)
        assertEquals("certificate", result.certificate)
        assertEquals("cover", result.cover?.value)
        assertEquals("dirName", result.directors?.get(0)?.name)
        assertEquals("nm845665566", result.directors?.get(0)?.nameId?.value)
        assertEquals("genres", result.genres)
        assertEquals(null, result.gross)
        assertEquals("8.9", result.rate)
        assertEquals("120", result.runtime)
        assertEquals("starName", result.stars?.get(0)?.name)
        assertEquals("nm568798", result.stars?.get(0)?.nameId?.value)
        assertEquals("summary", result.summary)
        assertEquals("title", result.title)
        assertEquals("tt568793", result.titleId?.value)
        assertEquals("59878", result.voteCount)
        assertEquals(null, result.videoId)
        assertEquals(null, result.videoName)
        assertEquals(null, result.videoPreview)
        assertEquals(null, result.videoRuntime)

    }

}