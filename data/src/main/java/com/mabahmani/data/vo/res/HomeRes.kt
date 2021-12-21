package com.mabahmani.data.vo.res

import com.mabahmani.domain.vo.Home
import com.mabahmani.domain.vo.common.*
import com.mabahmani.domain.vo.enum.HomeMediaType

data class HomeRes(
    val boxOffice: BoxOffice,
    val editorPicks: List<EditorPick>,
    val featuredToday: List<FeaturedToday>,
    val imdbOriginals: List<ImdbOriginal>,
    val news: List<News>,
    val trailers: List<Trailer>
) {
    data class BoxOffice(
        val weekendStartDate: String,
        val weekendEndDate: String,
        val data: List<Data>
    ) {
        data class Data(
            val weekendGross: Int,
            val currency: String,
            val cinemas: Int,
            val title: String,
            val titleId: String,

            )
    }

    data class EditorPick(
        val caption: String,
        val cover: String,
        val id: String,
        val image: Boolean,
        val link: String,
        val rmId: String,
        val title: String,
        val video: Boolean
    )

    data class FeaturedToday(
        val caption: String,
        val cover: String,
        val id: String,
        val image: Boolean,
        val link: String,
        val rmId: String,
        val title: String,
        val video: Boolean
    )

    data class ImdbOriginal(
        val caption: String,
        val cover: String,
        val id: String,
        val image: Boolean,
        val link: String,
        val rmId: String,
        val title: String,
        val video: Boolean
    )

    data class News(
        val id: String,
        val date: String,
        val image: String,
        val source: String,
        val title: String
    )


    data class Trailer(
        val title: String,
        val subTitle: String,
        val cover: String,
        val titleId: String,
        val videoId: String,
        val preview: String,
        val duration: String,
    )

    fun toHome(): Home {
        return Home(
            trailers.map {
                Trailer(
                    it.title,
                    it.subTitle,
                    TitleId(it.titleId),
                    VideoId(it.videoId),
                    it.duration,
                    Image(it.preview),
                    Image(it.cover)
                )
            },
            featuredToday.map {
                val mediaType: HomeMediaType = when {
                    it.video -> HomeMediaType.VIDEO
                    it.rmId.isNotEmpty() -> HomeMediaType.GALLERY
                    else -> HomeMediaType.LIST
                }
                Home.Media(it.title, it.caption, mediaType, Image(it.cover), it.id)
            },
            imdbOriginals.map {
                val mediaType: HomeMediaType = when {
                    it.video -> HomeMediaType.VIDEO
                    it.rmId.isNotEmpty() -> HomeMediaType.GALLERY
                    else -> HomeMediaType.LIST
                }
                Home.Media(it.title, it.caption, mediaType, Image(it.cover), it.id)
            },
            editorPicks.map {
                val mediaType: HomeMediaType = when {
                    it.video -> HomeMediaType.VIDEO
                    it.rmId.isNotEmpty() -> HomeMediaType.GALLERY
                    else -> HomeMediaType.LIST
                }
                Home.Media(it.title, it.caption, mediaType, Image(it.cover), it.id)
            },
            BoxOffice(
                boxOffice.weekendStartDate,
                boxOffice.weekendEndDate,
                boxOffice.data.map {
                    com.mabahmani.domain.vo.common.BoxOffice.BoxOfficeItem(
                        it.weekendGross.toString(),
                        "",
                        "",
                        it.title,
                        TitleId(it.titleId),
                        null
                    )
                }
            ),
            news.map {
                News(
                    it.date, Image(it.image), it.title, it.source, NewsId(it.id)
                )
            }
        )
    }
}