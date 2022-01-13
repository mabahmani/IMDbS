package com.mabahmani.data.vo.res

import com.mabahmani.domain.vo.enum.GuideRateType
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TitleParentalGuideResTest {
    private lateinit var model: TitleParentsGuideRes

    @Before
    fun setup() {
        model = TitleParentsGuideRes(
            listOf(
                TitleParentsGuideRes.Certification(
                    "cerSubtitle", "cerTitle"
                )
            ),
            "cover",
            listOf(
                TitleParentsGuideRes.NoSpoilGuide(
                    listOf("guides"),
                    "guideTitle",
                    "sever"
                )
            ),
            listOf(
                TitleParentsGuideRes.SpoilGuide(
                    listOf("guides"),
                    "guideTitle",
                    "mild"
                )
            ),
            "title",
            "1995"
        )
    }

    @Test
    fun `test TitleParentsGuideRes model to TitleParentsGuide domain model  conversion`() {
        val result = model.toTitleParentsGuide()

        assertEquals("cover", result.cover.value)
        assertEquals("title", result.name)
        assertEquals("1995", result.year)
        assertEquals("cerTitle", result.certifications[0].title)
        assertEquals("cerSubtitle", result.certifications[0].subtitle)
        assertEquals(GuideRateType.MILD, result.spoilGuides[0].guideRateType)
        assertEquals("guideTitle", result.spoilGuides[0].title)
        assertEquals("guides", result.spoilGuides[0].guides[0])
        assertEquals(GuideRateType.SEVER, result.noSpoilGuides[0].guideRateType)
        assertEquals("guideTitle", result.noSpoilGuides[0].title)
        assertEquals("guides", result.noSpoilGuides[0].guides[0])


    }

}