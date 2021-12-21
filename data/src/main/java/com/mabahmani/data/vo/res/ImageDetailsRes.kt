package com.mabahmani.data.vo.res

import com.mabahmani.domain.vo.ImageDetails
import com.mabahmani.domain.vo.common.*

data class ImageDetailsRes(
    val endCursor: String,
    val hasNextPage: Boolean,
    val hasPreviousPage: Boolean,
    val images: List<Image>,
    val startCursor: String,
    val title: String
) {
    data class Image(
        val caption: String,
        val copyRight: String,
        val countries: List<String>,
        val createdBy: String,
        val descriptionHtml: String,
        val id: String,
        val languages: List<String>,
        val names: List<Name>,
        val position: Int,
        val titles: List<Title>,
        val url: String
    ) {
        data class Name(
            val text: String,
            val id: String
        )

        data class Title(
            val text: String,
            val id: String
        )
    }


    fun toImageDetails():ImageDetails{
        return ImageDetails(
            endCursor,
            hasNextPage,
            hasPreviousPage,
            images.map { ImageDetails.ImageItemData(it.caption,it.descriptionHtml,
                Image(it.url), ImageId(it.id),
                it.titles.map { TitleLink(it.text, TitleId( it.id)) },
                it.names.map { NameLink(it.text, NameId( it.id)) },
                it.countries,
                it.languages,
                it.copyRight,
                it.createdBy
            ) },
            startCursor,
            title
        )
    }
}