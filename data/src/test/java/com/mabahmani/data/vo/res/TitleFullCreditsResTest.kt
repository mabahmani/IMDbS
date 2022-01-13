package com.mabahmani.data.vo.res

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TitleFullCreditsResTest {
    private lateinit var model: TitleFullCreditsRes

    @Before
    fun setup() {
        model = TitleFullCreditsRes(
            "cover",
            listOf(
                TitleFullCreditsRes.Credit(
                    listOf(TitleFullCreditsRes.Credit.Item("credit item id", "credit item image", "credit item subtitle", "credit item title")),
                    "credit title"
                    )
            ),
            "title",
            "1986"
        )
    }

    @Test
    fun `test TitleFullCreditsRes model to TitleFullCasts domain model  conversion`() {
        val result = model.toTitleFullCasts()

        assertEquals("cover", result.cover.value)
        assertEquals("title", result.name)
        assertEquals("1986", result.year)
        assertEquals("credit title", result.casts[0].title)
        assertEquals("credit item image", result.casts[0].casts[0].avatar.value)
        assertEquals("credit item subtitle", result.casts[0].casts[0].description)
        assertEquals("credit item title", result.casts[0].casts[0].name)
        assertEquals("credit item id", result.casts[0].casts[0].nameId.value)

    }

}