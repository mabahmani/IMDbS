package com.mabahmani.data.vo.res

import com.mabahmani.domain.vo.EventDetails
import com.mabahmani.domain.vo.common.Image

data class EventDetailsRes(
    val awards: List<Award>,
    val title: String,
    val subtitle: String,
    val year: String
) {
    data class Award(
        val name: String,
        val awardCategories: List<AwardCategory>,
    ) {
        data class AwardCategory(
            val title: String,
            val awardNominations: List<AwardNomination>
        ) {
            data class AwardNomination(
                val notes: String,
                val songNames: List<String>,
                val episodeNames: List<String>,
                val winner: Boolean,
                val primaryNominees: List<Nominee>,
                val secondaryNominees: List<Nominee>,
            ) {
                data class Nominee(
                    val name: String,
                    val note: String,
                    val imageUrl: String,
                    val id: String
                )
            }
        }

    }

    fun toEventDetails(): EventDetails {
        return EventDetails(
            title,
            subtitle,
            year,
            awards.map {
                EventDetails.Award(it.name,
                    it.awardCategories.map {
                        EventDetails.Award.AwardCategory(it.title,
                            it.awardNominations.map {
                                EventDetails.Award.AwardCategory.Nominee(
                                    it.winner,
                                    Image(it.primaryNominees[0].imageUrl),
                                    it.notes,
                                    it.primaryNominees[0].id,
                                    it.primaryNominees[0].name,
                                    it.primaryNominees.map {
                                        EventDetails.Award.AwardCategory.Nominee.SubNominee(
                                            Image(it.imageUrl),
                                            it.note,
                                            it.id,
                                            it.name
                                        )
                                    },

                                    it.secondaryNominees.map {
                                        EventDetails.Award.AwardCategory.Nominee.SubNominee(
                                            Image(it.imageUrl),
                                            it.note,
                                            it.id,
                                            it.name
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