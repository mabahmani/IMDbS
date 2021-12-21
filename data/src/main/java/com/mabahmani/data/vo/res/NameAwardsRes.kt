package com.mabahmani.data.vo.res

import com.mabahmani.domain.vo.NameAwards
import com.mabahmani.domain.vo.common.EventId
import com.mabahmani.domain.vo.common.Image
import com.mabahmani.domain.vo.common.TitleId
import com.mabahmani.domain.vo.common.TitleLink

data class NameAwardsRes(
    val avatar: String,
    val events: List<Event>,
    val name: String
) {
    data class Event(
        val awards: List<Award>,
        val title: String,
    ) {
        data class Award(
            val year: Year,
            val awardOutcomes: List<AwardOutcome>
        ) {

            data class Year(val id: String, val year: String)

            data class AwardOutcome(
                val title: String,
                val subtitle: String,
                val awardDescriptions: List<AwardDescription>
            ) {

                data class AwardDescription(
                    val description: String,
                    val titles: List<AwardItem>
                ) {
                    data class AwardItem(
                        val id: String,
                        val title: String,
                        val titleYear: String
                    )
                }
            }


        }
    }

    fun toNameAwards(): NameAwards {
        return NameAwards(name, Image(avatar),
            events.map {
                NameAwards.Event(
                    it.title,
                    it.awards.map {
                        NameAwards.Event.Award(
                            EventId(it.year.id),
                            it.year.year,
                            it.awardOutcomes.map {
                                NameAwards.Event.Award.AwardOutcome(
                                    it.title,
                                    it.subtitle,
                                    it.awardDescriptions.map {
                                        NameAwards.Event.Award.AwardOutcome.AwardDetail(
                                            it.description,
                                            it.titles.map {
                                                NameAwards.Event.Award.AwardOutcome.AwardDetail.AwardTitle(
                                                    TitleLink(it.title, TitleId(it.id)),
                                                    it.titleYear
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
        )
    }
}