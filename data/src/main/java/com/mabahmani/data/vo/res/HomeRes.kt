package com.mabahmani.data.vo.res

import com.mabahmani.domain.util.orFalse
import com.mabahmani.domain.util.orZero
import com.mabahmani.domain.vo.Home
import com.mabahmani.domain.vo.common.*
import com.mabahmani.domain.vo.enum.HomeMediaType
import java.text.SimpleDateFormat
import java.util.*

data class HomeRes(
    val boxOffice: BoxOffice?,
    val editorPicks: List<EditorPick>?,
    val featuredToday: List<FeaturedToday>?,
    val imdbOriginals: List<ImdbOriginal>?,
    val news: List<News>?,
    val trailers: List<Trailer>?
) {
    data class BoxOffice(
        val weekendStartDate: String?,
        val weekendEndDate: String?,
        val data: List<Data>? = null
    ) {
        data class Data(
            val weekendGross: Int?,
            val currency: String?,
            val cinemas: Int?,
            val title: String?,
            val titleId: String?,

            )
    }

    data class EditorPick(
        val caption: String?,
        val cover: String?,
        val id: String?,
        val image: Boolean?,
        val link: String?,
        val rmId: String?,
        val title: String?,
        val video: Boolean?
    )

    data class FeaturedToday(
        val caption: String?,
        val cover: String?,
        val id: String?,
        val image: Boolean?,
        val link: String?,
        val rmId: String?,
        val title: String?,
        val video: Boolean?
    )

    data class ImdbOriginal(
        val caption: String?,
        val cover: String?,
        val id: String?,
        val image: Boolean?,
        val link: String?,
        val rmId: String?,
        val title: String?,
        val video: Boolean?
    )

    data class News(
        val id: String?,
        val date: String?,
        val image: String?,
        val source: String?,
        val title: String?
    )


    data class Trailer(
        val title: String?,
        val subTitle: String?,
        val cover: String?,
        val titleId: String?,
        val videoId: String?,
        val preview: String?,
        val duration: String?,
    )

    fun toHome(): Home {

        val newsDateSourceFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val newsDateTargetFormatter = SimpleDateFormat("MMM dd", Locale.US)

        return Home(
            trailers?.map {
                Trailer(
                    it.title.orEmpty(),
                    it.subTitle.orEmpty(),
                    TitleId(it.titleId.orEmpty()),
                    VideoId(it.videoId.orEmpty()),
                    it.duration.orEmpty(),
                    Image(it.preview.orEmpty()),
                    Image(it.cover.orEmpty())
                )
            }?: mutableListOf(),
            featuredToday?.map {
                val mediaType: HomeMediaType = when {
                    it.video.orFalse() -> HomeMediaType.VIDEO
                    it.rmId.isNullOrEmpty().orFalse() -> HomeMediaType.GALLERY
                    else -> HomeMediaType.LIST
                }
                Home.Media(it.title.orEmpty(), it.caption.orEmpty(), mediaType, Image(it.cover.orEmpty()), it.id.orEmpty())
            }?: mutableListOf(),
            imdbOriginals?.map {
                val mediaType: HomeMediaType = when {
                    it.video.orFalse() -> HomeMediaType.VIDEO
                    it.rmId.isNullOrEmpty().orFalse() -> HomeMediaType.GALLERY
                    else -> HomeMediaType.LIST
                }
                Home.Media(it.title.orEmpty(), it.caption.orEmpty(), mediaType, Image(it.cover.orEmpty()), it.id.orEmpty())
            }?: mutableListOf(),
            editorPicks?.map {
                val mediaType: HomeMediaType = when {
                    it.video.orFalse()  -> HomeMediaType.VIDEO
                    it.rmId.isNullOrEmpty().orFalse() -> HomeMediaType.GALLERY
                    else -> HomeMediaType.LIST
                }
                Home.Media(it.title.orEmpty(), it.caption.orEmpty(), mediaType, Image(it.cover.orEmpty()), it.id.orEmpty())
            }?: mutableListOf(),
            com.mabahmani.domain.vo.common.BoxOffice(
                boxOffice?.weekendStartDate.orEmpty(),
                boxOffice?.weekendEndDate.orEmpty(),
                boxOffice?.data?.map {
                    com.mabahmani.domain.vo.common.BoxOffice.BoxOfficeItem(
                        it.weekendGross.orZero().toString(),
                        "",
                        "",
                        it.title.orEmpty(),
                        TitleId(it.titleId.orEmpty()),
                        null
                    )
                }
            ),
            news?.map {

                val date: String = try {
                    newsDateTargetFormatter.format(newsDateSourceFormatter.parse(it.date))
                }catch (ex: Exception){
                    ""
                }

                News(
                    date, Image(it.image.orEmpty()), it.title.orEmpty(), it.source.orEmpty(), NewsId(it.id.orEmpty())
                )
            }?: mutableListOf()
        )
    }
}
