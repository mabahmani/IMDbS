package com.mabahmani.data.vo.res

import com.mabahmani.domain.vo.TitleTechnicalSpecs
import com.mabahmani.domain.vo.common.Image
import com.mabahmani.domain.vo.common.Text

data class TitleTechnicalSpecsRes(
    val cover: String?,
    val specs: List<Spec>?,
    val title: String?,
    val year: String?
) {
    data class Spec(
        val subtitle: String?,
        val title: String?
    )

    fun toTitleTechnicalSpecs(): TitleTechnicalSpecs{
        return TitleTechnicalSpecs(
            title.orEmpty(),
            year.orEmpty(),
            Image(cover.orEmpty()),
            specs?.map { Text(it.title.orEmpty(),it.subtitle.orEmpty()) }?: listOf()
        )
    }
}