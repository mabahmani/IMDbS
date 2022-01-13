package com.mabahmani.data.vo.res

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SuggestResTest {
    private lateinit var model: SuggestRes

    @Before
    fun setup() {
        model = SuggestRes(
            listOf(
                SuggestRes.D(
                    SuggestRes.D.I(1080,"imageUrl", 1920),
                    "id",
                    "l",
                    "q",
                    5,
                    "s",
                    listOf(SuggestRes.D.V(SuggestRes.D.V.I(1080,"imageUrl", 1920),"viId","i","s")),
                    12,
                    1998
                )
            ),
            "q",
            1
        )
    }

    @Test
    fun `test SearchTitlesRes model to Title domain model  conversion`() {
        val result = model.toSuggestion()

        assertEquals("s", result[0].caption)
        assertEquals("id", result[0].id)
        assertEquals("imageUrl", result[0].image.value)
        assertEquals("l", result[0].name)
        assertEquals("1998", result[0].year)
        assertEquals("imageUrl", result[0].videos[0].preview.value)
        assertEquals("i", result[0].videos[0].title)
        assertEquals("s", result[0].videos[0].runtime)
        assertEquals("viId", result[0].videos[0].videoId.value)


    }

}