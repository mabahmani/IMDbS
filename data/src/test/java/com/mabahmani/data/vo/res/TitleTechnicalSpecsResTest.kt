package com.mabahmani.data.vo.res

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TitleTechnicalSpecsResTest {
    private lateinit var model: TitleTechnicalSpecsRes

    @Before
    fun setup() {
        model = TitleTechnicalSpecsRes(
            "cover",
            listOf(
                TitleTechnicalSpecsRes.Spec(
                    "techSubtitle",
                    "techTitle"
                )
            ),
            "title",
            "1995"
        )
    }

    @Test
    fun `test TitleTechnicalSpecsRes model to TitleTechnicalSpecs domain model  conversion`() {
        val result = model.toTitleTechnicalSpecs()

        assertEquals("cover", result.cover.value)
        assertEquals("title", result.name)
        assertEquals("1995", result.year)
        assertEquals("techTitle", result.items[0].title)
        assertEquals("techSubtitle", result.items[0].subtitle)

    }

}