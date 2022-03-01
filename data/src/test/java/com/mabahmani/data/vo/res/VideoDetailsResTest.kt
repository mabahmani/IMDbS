package com.mabahmani.data.vo.res

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class VideoDetailsResTest {
    private lateinit var model: VideosRes

    @Before
    fun setup() {
        model = VideosRes(
            "avatar",
            "title",
            listOf(
                VideosRes.Video(
                    "videoCover",
                    "vi568999",
                    "videoTitle"
                )
            )
        )
    }

    @Test
    fun `test VideosRes model to Videos domain model  conversion`() {
        val result = model.toVideos()

        assertEquals("vi568999", result[0].videoId.value)
        assertEquals("", result[0].runtime)
        assertEquals("videoTitle", result[0].title)
        assertEquals("videoCover", result[0].preview.value)

    }

}