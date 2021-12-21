package com.mabahmani.data.vo.res

import com.mabahmani.domain.vo.TitleParentsGuide
import com.mabahmani.domain.vo.common.Image
import com.mabahmani.domain.vo.common.Text
import com.mabahmani.domain.vo.enum.GuideRateType

data class TitleParentsGuideRes(
    val certifications: List<Certification>,
    val cover: String,
    val noSpoilGuides: List<NoSpoilGuide>,
    val spoilGuides: List<SpoilGuide>,
    val title: String,
    val year: String
) {
    data class Certification(
        val subtitle: String,
        val title: String
    )

    data class NoSpoilGuide(
        val items: List<String>,
        val title: String,
        val typeRate: String
    )

    data class SpoilGuide(
        val items: List<String>,
        val title: String,
        val typeRate: String
    )

    fun toTitleParentsGuide(): TitleParentsGuide{
        return TitleParentsGuide(
            title,
            year,
            Image(cover),
            certifications.map { Text(it.title, it.subtitle) },
            spoilGuides.map {
                val guideRateType =
                    when {
                        it.typeRate.equals("Mild", ignoreCase = true) -> GuideRateType.MILD
                        it.typeRate.equals("Moderate", ignoreCase = true) -> GuideRateType.MODERATE
                        else -> GuideRateType.SEVER
                    }
                TitleParentsGuide.Guide(it.title,guideRateType,it.items)
            },
            noSpoilGuides.map { val guideRateType =
                when {
                    it.typeRate.equals("Mild", ignoreCase = true) -> GuideRateType.MILD
                    it.typeRate.equals("Moderate", ignoreCase = true) -> GuideRateType.MODERATE
                    else -> GuideRateType.SEVER
                }
                TitleParentsGuide.Guide(it.title,guideRateType,it.items) }
        )
    }
}