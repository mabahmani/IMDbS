package com.mabahmani.domain.vo

import com.mabahmani.domain.vo.common.*
import com.mabahmani.domain.vo.enum.HomeMediaType

data class Home(
    val trailers: List<Trailer>,
    val featuredToday: List<Media>,
    val imdbOriginals: List<Media>,
    val editorPicks: List<Media>,
    val boxOffice: BoxOffice,
    val news: List<News>,

    ) {

    data class Media(
        val title: String,
        val caption: String,
        val type: HomeMediaType,
        val image: Image,
        val id: String,
        val date: String? = null
    )

}