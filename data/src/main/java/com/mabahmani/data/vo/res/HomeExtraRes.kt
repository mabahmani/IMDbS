package com.mabahmani.data.vo.res

import com.mabahmani.data.util.toHumanReadableTime
import com.mabahmani.domain.vo.HomeExtra
import com.mabahmani.domain.vo.common.*
import java.text.SimpleDateFormat
import java.util.*

data class HomeExtraRes(
    val bornTodayList: List<BornToday>,
    val comingSoonMovies: List<ComingSoonMovie>,
    val fanPicksTitles: List<FanPicksTitle>,
    val showTimesTitles: List<ShowTimesTitle>,
    val streamingTitles: List<StreamingTitle>
) {
    data class BornToday(
        val birth: String?,
        val death: String?,
        val image: String?,
        val nameId: String?,
        val title: String?
    )

    data class ComingSoonMovie(
        val certificate: String?,
        val cover: String?,
        val rate: Float?,
        val releaseDay: String?,
        val releaseMonth: String?,
        val releaseYear: String?,
        val runtime: Int?,
        val title: String?,
        val titleId: String?,
        val videoId: String?,
        val videoName: String?,
        val videoPreview: String?,
        val videoRuntime: Int?,
        val voteCount: Int?
    )

    data class FanPicksTitle(
        val certificate: String?,
        val cover: String?,
        val rate: Float?,
        val releaseDay: String?,
        val releaseMonth: String?,
        val releaseYear: String?,
        val runtime: Int?,
        val title: String?,
        val titleId: String?,
        val videoId: String?,
        val voteCount: Int?
    )

    data class ShowTimesTitle(
        val certificate: String?,
        val cover: String?,
        val rate: Float?,
        val releaseDay: String?,
        val releaseMonth: String?,
        val releaseYear: String?,
        val runtime: Int?,
        val title: String?,
        val titleId: String?,
        val videoId: String?,
        val voteCount: Int?
    )

    data class StreamingTitle(
        val name: String?,
        val titles: List<Title>
    ) {
        data class Title(
            val certificate: String?,
            val cover: String?,
            val rate: Float?,
            val releaseDay: String?,
            val releaseMonth: String?,
            val releaseYear: String?,
            val runtime: Int?,
            val title: String?,
            val titleId: String?,
            val videoId: String?,
            val voteCount: Int?
        )
    }


    fun toHomeExtra(): HomeExtra {

        val thisYear = SimpleDateFormat("yyyy", Locale.US).format(Date())

        return HomeExtra(
            fanPicksTitles.map {
                Title(
                    Image(it.cover.toString()),
                    it.rate.toString(),
                    it.voteCount.toString(),
                    null,
                    null,
                    it.runtime.toHumanReadableTime(),
                    it.title,
                    it.releaseYear,
                    it.releaseMonth,
                    it.releaseDay,
                    it.certificate,
                    null,
                    null,
                    null,
                    TitleId(it.titleId.toString()),
                    VideoId(it.videoId.toString()),
                    null,
                    null,
                    null
                )
            },
            comingSoonMovies.map {
                Title(
                    Image(it.cover.toString()),
                    it.rate.toString(),
                    it.voteCount.toString(),
                    null,
                    null,
                    it.runtime.toHumanReadableTime(),
                    it.title,
                    it.releaseYear,
                    it.releaseMonth,
                    it.releaseDay,
                    it.certificate,
                    null,
                    null,
                    null,
                    TitleId(it.titleId.toString()),
                    VideoId(it.videoId.toString()),
                    it.videoName.orEmpty(),
                    Image(it.videoPreview.orEmpty()),
                    it.videoRuntime?.toHumanReadableTime()
                )
            },
            showTimesTitles.map {
                Title(
                    Image(it.cover.toString()),
                    it.rate.toString(),
                    it.voteCount.toString(),
                    null,
                    null,
                    it.runtime.toHumanReadableTime(),
                    it.title,
                    it.releaseYear,
                    it.releaseMonth,
                    it.releaseDay,
                    it.certificate,
                    null,
                    null,
                    null,
                    TitleId(it.titleId.toString()),
                    VideoId(it.videoId.toString()),
                    null,
                    null,
                    null
                )
            },
            streamingTitles.map {
                HomeExtra.StreamProvider(
                    it.name.toString(),
                    it.titles.map {
                        Title(
                            Image(it.cover.toString()),
                            it.rate.toString(),
                            it.voteCount.toString(),
                            null,
                            null,
                            it.runtime.toHumanReadableTime(),
                            it.title,
                            it.releaseYear,
                            it.releaseMonth,
                            it.releaseDay,
                            it.certificate,
                            null,
                            null,
                            null,
                            TitleId(it.titleId.toString()),
                            VideoId(it.videoId.toString()),
                            null,
                            null,
                            null
                        )
                    })
            },
            bornTodayList.map {

                val birthYear = it.birth?.slice(0..3).orEmpty()
                val deathYear = it.death?.slice(0..3).orEmpty()

                val age: String = try {
                    (thisYear.toInt() - birthYear.toInt()).toString()
                } catch (ex: Exception) {
                    "0"
                }

                HomeExtra.BornToday(
                    Image(it.image.toString()),
                    it.title.toString(),
                    birthYear,
                    deathYear,
                    age,
                    NameId(it.nameId.toString())
                )
            }
        )
    }
}