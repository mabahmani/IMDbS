package com.mabahmani.data.vo.res

import com.mabahmani.domain.vo.common.*

data class SearchTitlesRes(
    val certificate: String,
    val cover: String,
    val directors: List<Director>,
    val genres: String,
    val imdbRating: String,
    val numberOfVotes: String,
    val position: String,
    val runtime: String,
    val stars: List<Star>,
    val summary: String,
    val title: String,
    val titleId: String,
    val year: String
) {
    data class Director(
        val name: String,
        val nameId: String
    )

    data class Star(
        val name: String,
        val nameId: String
    )

    fun toTitle(): Title{
        return Title(
            Image(cover),
            imdbRating,
            numberOfVotes,
            null,
            genres,
            runtime,
            title,
            year,
            null,
            null,
            certificate,
            summary,
            stars.map { NameLink(it.name, NameId(it.nameId)) },
            directors.map { NameLink(it.name, NameId(it.nameId)) },
            TitleId(titleId),
            null
        )
    }
}