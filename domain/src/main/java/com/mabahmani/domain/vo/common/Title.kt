package com.mabahmani.domain.vo.common

data class Title(
    val cover: Image?,
    val rate: String?,
    val voteCount: String?,
    val gross: String?,
    val genres: String?,
    val runtime: String?,
    val title: String?,
    val releaseYear: String?,
    val releaseMonth: String?,
    val releaseDay: String?,
    val certificate: String?,
    val summary: String?,
    val starts:List<NameLink>?,
    val directors:List<NameLink>?,
    val titleId: TitleId?,
    val videoId: VideoId?
)