package com.mabahmani.domain.vo

import com.mabahmani.domain.vo.common.Image
import com.mabahmani.domain.vo.common.NameId
import com.mabahmani.domain.vo.common.Title

data class HomeExtra(
    val fanPicksTitles: List<Title>,
    val comingSoonTitles: List<Title>,
    val showTimesTitles: List<Title>,
    val streamingTitles: List<StreamProvider>,
    val bornTodayList: List<BornToday>,
){

    data class StreamProvider(
        val name: String,
        val movies: List<Title>
    )

    data class BornToday(
        val avatar: Image,
        val name: String,
        val birth: String,
        val death: String,
        val nameId: NameId
    )
}
