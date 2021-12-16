package com.mabahmani.domain

import com.mabahmani.domain.vo.common.Image
import org.junit.Assert.assertEquals
import org.junit.Test

class ImageTests {

    private val imageUrl = "https://m.media-amazon.com/images/M/MV5BN2Y5NGE3MDAtMWU1Ni00NTVmLWE2M2QtMjk3Y2MzMmI0MTE5XkEyXkFqcGdeQWRvb2xpbmhk._V1_QL40_.jpg"

    @Test
    fun getOriginalImageSizeUrl(){
        val expectedValue = "https://m.media-amazon.com/images/M/MV5BN2Y5NGE3MDAtMWU1Ni00NTVmLWE2M2QtMjk3Y2MzMmI0MTE5XkEyXkFqcGdeQWRvb2xpbmhk.jpg"

        val originalImageSizeUrl = Image(imageUrl).getOriginalImageSizeUrl()

        assertEquals(expectedValue,originalImageSizeUrl)
    }

    @Test
    fun getCustomImageWidthUrl(){
        val expectedValue = "https://m.media-amazon.com/images/M/MV5BN2Y5NGE3MDAtMWU1Ni00NTVmLWE2M2QtMjk3Y2MzMmI0MTE5XkEyXkFqcGdeQWRvb2xpbmhk.UX800.jpg"

        val customImageSizeUrl = Image(imageUrl).getCustomImageWidthUrl(800)

        assertEquals(expectedValue,customImageSizeUrl)
    }

    @Test
    fun getCustomImageHeightUrl(){
        val expectedValue = "https://m.media-amazon.com/images/M/MV5BN2Y5NGE3MDAtMWU1Ni00NTVmLWE2M2QtMjk3Y2MzMmI0MTE5XkEyXkFqcGdeQWRvb2xpbmhk.UY500.jpg"

        val customImageSizeUrl = Image(imageUrl).getCustomImageHeightUrl(500)

        assertEquals(expectedValue,customImageSizeUrl)
    }
}