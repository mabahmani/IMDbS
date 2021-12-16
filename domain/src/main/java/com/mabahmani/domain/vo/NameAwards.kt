package com.mabahmani.domain.vo

import com.mabahmani.domain.vo.common.EventId
import com.mabahmani.domain.vo.common.Image
import com.mabahmani.domain.vo.common.TitleLink

data class NameAwards(
    val name: String,
    val avatar: Image,
    val events: List<Event>
) {
    data class Event(
        val name: String,
        val awards: List<Award>
    ){
        data class Award(
            val eventId: EventId,
            val eventYear: String,
            val awardOutcomes: List<AwardOutcome>
        ){
            data class AwardOutcome(
                val title: String,
                val subtitle: String,
                val awardDetails: List<AwardDetail>
            ){
                data class AwardDetail(
                    val description: String,
                    val awardTitles: List<AwardTitle>
                ){
                    data class AwardTitle(
                        val titleLink: TitleLink,
                        val titleReleaseYear: String
                    )
                }
            }
        }
    }
}