package com.mabahmani.data.vo.res

import com.mabahmani.domain.vo.common.Image
import com.mabahmani.domain.vo.common.ImageId
import com.mabahmani.domain.vo.common.ImageLink

data class ImagesRes(
    val images: List<Image>,
    val subtitle: String,
    val title: String
) {
    data class Image(
        val id: String,
        val url: String
    )

    fun toImages(): List<ImageLink> {
        return images.map { ImageLink(Image(it.url), ImageId(it.id)) }
    }
}