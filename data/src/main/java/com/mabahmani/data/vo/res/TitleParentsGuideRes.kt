package com.mabahmani.data.vo.res

import com.mabahmani.domain.vo.TitleParentsGuide
import com.mabahmani.domain.vo.common.Image
import com.mabahmani.domain.vo.common.Text
import com.mabahmani.domain.vo.enum.GuideRateType

data class TitleParentsGuideRes(
    val certifications: List<Certification>?,
    val cover: String?,
    val noSpoilGuides: List<NoSpoilGuide>?,
    val spoilGuides: List<SpoilGuide>?,
    val title: String?,
    val year: String?
) {
    data class Certification(
        val subtitle: String?,
        val title: String?
    )

    data class NoSpoilGuide(
        val items: List<String>?,
        val title: String?,
        val typeRate: String?
    )

    data class SpoilGuide(
        val items: List<String>?,
        val title: String?,
        val typeRate: String?
    )

    fun toTitleParentsGuide(): TitleParentsGuide {
        return TitleParentsGuide(
            title.orEmpty(),
            year.orEmpty(),
            Image(cover.orEmpty()),
            certifications?.map { Text(it.title.orEmpty(), it.subtitle.orEmpty()) } ?: listOf(),
            spoilGuides?.map {
                TitleParentsGuide.Guide(it.title.orEmpty(), null, it.items.orEmpty())
            } ?: listOf(),
            noSpoilGuides?.map {
                val guideRateType =
                    when {
                        it.typeRate?.trim().equals("Mild", ignoreCase = true) -> GuideRateType.MILD
                        it.typeRate?.trim()
                            .equals("Moderate", ignoreCase = true) -> GuideRateType.MODERATE
                        it.typeRate?.trim()
                            .equals("Sever", ignoreCase = true) -> GuideRateType.SEVER
                        else -> null
                    }
                TitleParentsGuide.Guide(it.title.orEmpty(), guideRateType, it.items.orEmpty())
            } ?: listOf()
        )
    }
}