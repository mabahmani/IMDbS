package com.mabahmani.data.vo.res

import com.mabahmani.domain.util.orFalse
import com.mabahmani.domain.vo.ImageDetails
import com.mabahmani.domain.vo.common.*

data class ImageDetailsRes(
    val endCursor: String?,
    val hasNextPage: Boolean?,
    val hasPreviousPage: Boolean?,
    val images: List<Image>?,
    val startCursor: String?,
    val title: String?
) {
    data class Image(
        val caption: String?,
        val copyRight: String?,
        val countries: List<String>?,
        val createdBy: String?,
        val descriptionHtml: String?,
        val id: String?,
        val languages: List<String>?,
        val names: List<Name>?,
        val position: Int?,
        val titles: List<Title>?,
        val url: String?
    ) {
        data class Name(
            val text: String?,
            val id: String?
        )

        data class Title(
            val text: String?,
            val id: String?
        )
    }


    fun toImageDetails():ImageDetails{
        return ImageDetails(
            endCursor.orEmpty(),
            hasNextPage.orFalse(),
            hasPreviousPage.orFalse(),
            images?.map { ImageDetails.ImageItemData(it.caption.orEmpty(),it.descriptionHtml.orEmpty(),
                Image(it.url.orEmpty()), ImageId(it.id.orEmpty()),
                it.titles?.map { TitleLink(it.text.orEmpty(), TitleId( it.id.orEmpty())) }?: listOf(),
                it.names?.map { NameLink(it.text.orEmpty(), NameId( it.id.orEmpty())) }?: listOf(),
                it.countries.orEmpty(),
                it.languages.orEmpty(),
                it.copyRight.orEmpty(),
                it.createdBy.orEmpty()
            ) }?: listOf(),
            startCursor.orEmpty(),
            title.orEmpty()
        )
    }
}