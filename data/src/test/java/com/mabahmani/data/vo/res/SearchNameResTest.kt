package com.mabahmani.data.vo.res

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SearchNameResTest {
    private lateinit var model: SearchNameRes

    @Before
    fun setup() {
        model = SearchNameRes(
            "avatar",
            "name",
            "nm568877",
            "0",
            "summary",
            SearchNameRes.TopMovie(
                "role",
                "title",
                "tt58799877"
            )
        )
    }

    @Test
    fun `test SearchNameRes model to Name domain model  conversion`() {
        val result = model.toName()

        assertEquals("avatar", result.avatar.value)
        assertEquals("name", result.name)
        assertEquals("nm568877", result.nameId.value)
        assertEquals("summary", result.summary)
        assertEquals("role", result.topRole)
        assertEquals("title", result.topTitle.title)
        assertEquals("tt58799877", result.topTitle.titleId.value)

    }

}