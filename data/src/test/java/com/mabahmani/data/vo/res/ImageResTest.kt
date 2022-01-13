package com.mabahmani.data.vo.res

import com.mabahmani.domain.vo.enum.HomeMediaType
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ImageResTest {
    private lateinit var model: ImagesRes

    @Before
    fun setup() {
        model = ImagesRes(
            listOf(
                ImagesRes.Image(
                    "rm56564",
                    "url"
                )
            ),
            "subtitle",
            "title"
        )
    }

    @Test
    fun `test ImagesRes model to Images domain model  conversion`() {
        val result = model.toImages()

        assertEquals("url", result[0].image.value)
        assertEquals("rm56564", result[0].imageId?.value)

    }

}