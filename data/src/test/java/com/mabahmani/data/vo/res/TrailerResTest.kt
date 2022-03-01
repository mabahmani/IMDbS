package com.mabahmani.data.vo.res

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TrailerResTest {
    private lateinit var model: TrailerRes

    @Before
    fun setup() {
        model = TrailerRes(
            "title",
            "titleCover",
            "tt568798",
            5,
            10,
            1998,
            "videoDescription",
            "vi89455666",
            "videoName",
            50,
            "videoThumb"
        )
    }

    @Test
    fun `test TrailerRes model to Trailer domain model  conversion`() {
        val result = model.toTrailer()

        assertEquals("videoDescription", result.caption)
        assertEquals("50", result.duration)
        assertEquals("title", result.title)
        assertEquals("titleCover", result.titleCover.value)
        assertEquals("tt568798", result.titleId.value)
        assertEquals("vi89455666", result.videoId.value)
        assertEquals("videoThumb", result.videoPreview.value)

    }

}