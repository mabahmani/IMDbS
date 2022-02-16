package com.mabahmani.data.vo.res

import com.mabahmani.domain.vo.TitleFullCasts
import com.mabahmani.domain.vo.common.Cast
import com.mabahmani.domain.vo.common.Image
import com.mabahmani.domain.vo.common.NameId

data class TitleFullCreditsRes(
    val cover: String?,
    val credits: List<Credit>?,
    val title: String?,
    val year: String?
) {
    data class Credit(
        val items: List<Item>?,
        val title: String?
    ) {
        data class Item(
            val id: String?,
            val image: String?,
            val subtitle: String?,
            val title: String?
        )
    }

    fun toTitleFullCasts(): TitleFullCasts {
        return TitleFullCasts(
            title.orEmpty(),
            year.orEmpty(),
            Image(cover.orEmpty()),
            credits?.map {
                TitleFullCasts.CastItem(it.title.orEmpty(), it.items?.map {
                    Cast(
                        it.title.orEmpty(), it.subtitle.orEmpty(), Image(it.image.orEmpty()),
                        NameId(it.id.orEmpty())
                    )
                }?: listOf())
            }?: listOf()
        )
    }
}