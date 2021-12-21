package com.mabahmani.domain.vo

import com.mabahmani.domain.vo.common.Image
import com.mabahmani.domain.vo.common.ImageId
import com.mabahmani.domain.vo.common.NameLink
import com.mabahmani.domain.vo.common.TitleLink

data class ImageDetails(
    val endCursor: String,
    val hasNextPage: Boolean,
    val hasPreviousPage: Boolean,
    val images: List<ImageItemData>,
    val startCursor: String,
    val title: String
){
    data class ImageItemData(
        val title: String,
        val description: String,
        val image: Image,
        val imageId: ImageId,
        val titles: List<TitleLink>,
        val names: List<NameLink>,
        val countries: List<String>,
        val languages: List<String>,
        val copyRight: String,
        val createdBy: String
    )
}