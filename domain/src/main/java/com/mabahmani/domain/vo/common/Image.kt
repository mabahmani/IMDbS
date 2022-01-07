package com.mabahmani.domain.vo.common

data class Image(val value: String) {

    fun getOriginalImageSizeUrl(): String {

        if (value.isNotEmpty()) {
            return try {
                val urlWithoutJPG = value.replace(".jpg", "")
                val baseUrl = urlWithoutJPG.substring(0,urlWithoutJPG.lastIndexOf("."))
                "$baseUrl.jpg"

            }catch (ex: Exception){
                value
            }

        }

        return value
    }

    fun getCustomImageWidthUrl(width: Int): String {

        if (value.isNotEmpty()) {
            val originalUrl = getOriginalImageSizeUrl()

            val urlWithoutJPG = originalUrl.replace(".jpg", "")

            return "$urlWithoutJPG.UX$width.jpg"
        }

        return value
    }

    fun getCustomImageHeightUrl(height: Int): String {

        if (value.isNotEmpty()) {
            val originalUrl = getOriginalImageSizeUrl()

            val urlWithoutJPG = originalUrl.replace(".jpg", "")

            return "$urlWithoutJPG.UY$height.jpg"
        }

        return value
    }

}
