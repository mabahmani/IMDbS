package com.mabahmani.data.vo.res

import com.mabahmani.domain.vo.common.*

data class SearchNameRes(
    val avatar: String?,
    val name: String?,
    val nameId: String?,
    val position: String?,
    val summary: String?,
    val topMovie: TopMovie?
) {
    data class TopMovie(
        val role: String?,
        val title: String?,
        val titleId: String?
    )

    fun toName(): Name{
        return Name(
            NameId(nameId.orEmpty()),
            name.orEmpty(),
            Image(avatar.orEmpty()),
            summary.orEmpty(),
            topMovie?.role.orEmpty(),
            TitleLink(topMovie?.title.orEmpty(), TitleId(topMovie?.titleId.orEmpty()))
        )
    }
}