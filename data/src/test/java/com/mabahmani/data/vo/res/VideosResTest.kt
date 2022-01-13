package com.mabahmani.data.vo.res

import com.mabahmani.domain.vo.enum.GuideRateType
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class VideosResTest {
    private lateinit var model: VideoDetailsRes

    @Before
    fun setup() {
        model = VideoDetailsRes(
            "highQ",
            "lowQ",
            listOf(
                VideoDetailsRes.RelatedVideo(
                    "rvCover",
                    "rvDuration",
                    "rvSubtitle",
                    "rvTitle",
                    "tt56879",
                    "vi564987"
                )
            ),
            "rcRating",
            "rcRatingBody",
            listOf("asd"),
            5.4f,
            false,
            true,
            "relationPoster",
            "1988-07-15",
            "1988",
            "120",
            "relationTitle",
            "tt5687898",
            "videoCover",
            "videoDescription",
            "2",
            "videoSubtitle",
            "videoTitle"
        )
    }

    @Test
    fun `test VideoDetailsRes model to VideoDetails domain model  conversion`() {
        val result = model.toVideoDetails()

        assertEquals("videoTitle", result.caption)
        assertEquals("highQ", result.highQualityUrl)
        assertEquals("lowQ", result.lowQualityUrl)
        assertEquals("videoCover", result.preview.value)
        assertEquals("relationPoster", result.relatedTitle.cover?.value)
        assertEquals("1988", result.relatedTitle.releaseYear)
        assertEquals("tt5687898", result.relatedTitle.titleId?.value)
        assertEquals("relationTitle", result.relatedTitle.title)
        assertEquals("120", result.relatedTitle.runtime)
        assertEquals("5.4", result.relatedTitle.rate)
        assertEquals("rcRating", result.relatedTitle.certificate)
        assertEquals("[asd]", result.relatedTitle.genres)

    }

}