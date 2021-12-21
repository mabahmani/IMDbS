package com.mabahmani.data.vo.res

import com.mabahmani.domain.vo.HomeExtra
import com.mabahmani.domain.vo.common.*

data class HomeExtraRes(
    val bornTodayList: List<BornToday>,
    val comingSoonMovies: List<ComingSoonMovie>,
    val fanPicksTitles: List<FanPicksTitle>,
    val showTimesTitles: List<ShowTimesTitle>,
    val streamingTitles: List<StreamingTitle>
) {
    data class BornToday(
        val birth: String,
        val death: String,
        val image: String,
        val nameId: String,
        val title: String
    )

    data class ComingSoonMovie(
        val certificate: String,
        val cover: String,
        val rate: Int,
        val releaseDay: String,
        val releaseMonth: String,
        val releaseYear: String,
        val runtime: Int,
        val title: String,
        val titleId: String,
        val videoId: String,
        val voteCount: Int
    )

    data class FanPicksTitle(
        val certificate: String,
        val cover: String,
        val rate: Int,
        val releaseDay: String,
        val releaseMonth: String,
        val releaseYear: String,
        val runtime: Int,
        val title: String,
        val titleId: String,
        val videoId: String,
        val voteCount: Int
    )

    data class ShowTimesTitle(
        val certificate: String,
        val cover: String,
        val rate: Int,
        val releaseDay: String,
        val releaseMonth: String,
        val releaseYear: String,
        val runtime: Int,
        val title: String,
        val titleId: String,
        val videoId: String,
        val voteCount: Int
    )

    data class StreamingTitle(
        val name: String,
        val titles: List<Title>
    ) {
        data class Title(
            val certificate: String,
            val cover: String,
            val rate: Int,
            val releaseDay: String,
            val releaseMonth: String,
            val releaseYear: String,
            val runtime: Int,
            val title: String,
            val titleId: String,
            val videoId: String,
            val voteCount: Int
        )
    }


    fun toHomeExtra(): HomeExtra{
        return HomeExtra(
            fanPicksTitles.map { Title(Image(it.cover),it.rate.toString(),it.voteCount.toString(),null,null,it.runtime.toString(),it.title,it.releaseYear,it.releaseMonth,it.releaseDay,it.certificate,null,null,null, TitleId(it.titleId), VideoId(it.videoId)) },
            comingSoonMovies.map { Title(Image(it.cover),it.rate.toString(),it.voteCount.toString(),null,null,it.runtime.toString(),it.title,it.releaseYear,it.releaseMonth,it.releaseDay,it.certificate,null,null,null, TitleId(it.titleId), VideoId(it.videoId)) },
            showTimesTitles.map { Title(Image(it.cover),it.rate.toString(),it.voteCount.toString(),null,null,it.runtime.toString(),it.title,it.releaseYear,it.releaseMonth,it.releaseDay,it.certificate,null,null,null, TitleId(it.titleId), VideoId(it.videoId)) },
            streamingTitles.map { HomeExtra.StreamProvider(it.name, it.titles.map { Title(Image(it.cover),it.rate.toString(),it.voteCount.toString(),null,null,it.runtime.toString(),it.title,it.releaseYear,it.releaseMonth,it.releaseDay,it.certificate,null,null,null, TitleId(it.titleId), VideoId(it.videoId)) }) },
            bornTodayList.map { HomeExtra.BornToday(Image(it.image),it.title,it.birth,it.death, NameId(it.nameId)) }
        )
    }
}