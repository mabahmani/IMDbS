package com.mabahmani.data.vo.res

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NewsDetailsResTest {
    private lateinit var model: NewsDetailsRes

    @Before
    fun setup() {
        model = NewsDetailsRes(
            "author",
            "1980-05-10",
            "imageUrl",
            "link",
            "newsContent",
            "newsAgency",
            "newsTitle"
        )
    }

    @Test
    fun `test NewsDetailsRes model to NewsDetails domain model  conversion`() {
        val result = model.toNewsDetails()

        assertEquals("author", result.author)
        assertEquals("1980-05-10", result.date)
        assertEquals("imageUrl", result.image.value)
        assertEquals("link", result.link)
        assertEquals("newsContent", result.content)
        assertEquals("newsAgency", result.newsAgency)
        assertEquals("newsTitle", result.title)
    }

}