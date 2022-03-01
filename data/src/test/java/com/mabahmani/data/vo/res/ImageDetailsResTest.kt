package com.mabahmani.data.vo.res

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ImageDetailsResTest {
    private lateinit var model: ImageDetailsRes

    @Before
    fun setup() {
        model = ImageDetailsRes(
            "egs5asda689",
            true,
            false,
            listOf(
                ImageDetailsRes.Image(
                    "caption",
                    "copyRight",
                    listOf("countries"),
                    "createdBy",
                    "descriptionHtml",
                    "id",
                    listOf("language"),
                    listOf(
                        ImageDetailsRes.Image.Name(
                            "name",
                            "nm5656556"
                        )
                    ),
                    5,
                    listOf(
                        ImageDetailsRes.Image.Title(
                            "title",
                            "tt34242342"
                        )
                    ),
                    "url"
                )
            ),
            "asdf56899",
            "title"
        )
    }

    @Test
    fun `test ImageDetailsRes model to ImageDetails domain model  conversion`() {
        val result = model.toImageDetails()

        assertEquals("asdf56899", result.startCursor)
        assertEquals("egs5asda689", result.endCursor)
        assertEquals("title", result.title)
        assertTrue(result.hasNextPage)
        assertFalse(result.hasPreviousPage)
        assertEquals("copyRight", result.images[0].copyRight)
        assertEquals("countries", result.images[0].countries[0])
        assertEquals("language", result.images[0].languages[0])
        assertEquals("createdBy", result.images[0].createdBy)
        assertEquals("descriptionHtml", result.images[0].description)
        assertEquals("id", result.images[0].imageId.value)
        assertEquals("url", result.images[0].image.value)
        assertEquals("caption", result.images[0].title)
        assertEquals("name", result.images[0].names[0].name)
        assertEquals("nm5656556", result.images[0].names[0].nameId.value)
        assertEquals("tt34242342", result.images[0].titles[0].titleId.value)

    }

}