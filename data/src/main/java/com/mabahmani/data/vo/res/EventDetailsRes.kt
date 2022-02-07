package com.mabahmani.data.vo.res

import com.mabahmani.domain.util.orFalse
import com.mabahmani.domain.vo.EventDetails
import com.mabahmani.domain.vo.common.Image

data class EventDetailsRes(
    val awards: List<Award>?,
    val title: String?,
    val subtitle: String?,
    val year: String?
) {
    data class Award(
        val name: String?,
        val awardCategories: List<AwardCategory>?,
    ) {
        data class AwardCategory(
            val title: String?,
            val awardNominations: List<AwardNomination>?
        ) {
            data class AwardNomination(
                val notes: String?,
                val songNames: List<String>?,
                val episodeNames: List<String>?,
                val winner: Boolean?,
                val primaryNominees: List<Nominee>?,
                val secondaryNominees: List<Nominee>?,
            ) {
                data class Nominee(
                    val name: String?,
                    val note: String?,
                    val imageUrl: String?,
                    val id: String?
                )
            }
        }

    }

    fun toEventDetails(): EventDetails {
        return EventDetails(
            title.orEmpty(),
            subtitle.orEmpty(),
            year.orEmpty(),
            awards?.map {
                EventDetails.Award(it.name.orEmpty(),
                    it.awardCategories?.map {
                        EventDetails.Award.AwardCategory(it.title.orEmpty(),
                            it.awardNominations?.map {
                                EventDetails.Award.AwardCategory.Nominee(
                                    it.winner.orFalse(),
                                    Image(it.primaryNominees?.get(0)?.imageUrl.orEmpty()),
                                    it.notes.orEmpty(),
                                    it.primaryNominees?.get(0)?.id.orEmpty(),
                                    it.primaryNominees?.get(0)?.name.orEmpty(),
                                    it.primaryNominees?.map {
                                        EventDetails.Award.AwardCategory.Nominee.SubNominee(
                                            Image(it.imageUrl.orEmpty()),
                                            it.note.orEmpty(),
                                            it.id.orEmpty(),
                                            it.name.orEmpty()
                                        )
                                    }?: listOf(),

                                    it.secondaryNominees?.map {
                                        EventDetails.Award.AwardCategory.Nominee.SubNominee(
                                            Image(it.imageUrl.orEmpty()),
                                            it.note.orEmpty(),
                                            it.id.orEmpty(),
                                            it.name.orEmpty()
                                        )
                                    }?: listOf()
                                )
                            }?: listOf()
                        )
                    }?: listOf()
                )
            }?: listOf()

        )
    }
}