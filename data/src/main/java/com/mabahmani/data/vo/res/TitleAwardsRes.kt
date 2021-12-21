package com.mabahmani.data.vo.res

import com.mabahmani.domain.vo.TitleAwards
import com.mabahmani.domain.vo.common.EventId
import com.mabahmani.domain.vo.common.Image
import com.mabahmani.domain.vo.common.NameId
import com.mabahmani.domain.vo.common.NameLink

data class TitleAwardsRes(
    val cover: String,
    val events: List<Event>,
    val title: String,
    val year: String
) {
    data class Event(
        val awards: List<Award>,
        val eventId: String,
        val title: String,
        val year: String
    ) {
        data class Award(
            val awardCategory: String,
            val awardDescriptions: List<AwardDescription>,
            val awardOutcome: String
        ) {
            data class AwardDescription(
                val awardItems: List<AwardItem>,
                val note: String,
                val title: String
            ) {
                data class AwardItem(
                    val id: String,
                    val title: String
                )
            }
        }
    }

    fun toTitleAward(): TitleAwards {
        return TitleAwards(
            title,
            Image(cover),
            events.map {
                TitleAwards.Event(
                    it.title,
                    it.awards.map { award ->
                        TitleAwards.Event.Award(
                            EventId(it.eventId),
                            it.year,
                            award.awardDescriptions.map { awardDescription ->
                                TitleAwards.Event.Award.AwardOutcome(
                                    award.awardCategory,
                                    award.awardOutcome,
                                    awardDescription.awardItems.map {
                                        TitleAwards.Event.Award.AwardOutcome.AwardDetail(
                                            awardDescription.title,
                                            awardDescription.awardItems.map {
                                                NameLink(it.title, NameId(it.id))
                                            }

                                        )
                                    }
                                )
                            }

                        )
                    }
                )
            }
        )
    }
}