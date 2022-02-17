package com.mabahmani.domain.vo

import com.mabahmani.domain.vo.common.Image
import com.mabahmani.domain.vo.common.Text
import com.mabahmani.domain.vo.enum.GuideRateType

data class TitleParentsGuide(
    val name: String,
    val year: String,
    val cover: Image,
    val certifications: List<Text>,
    val spoilGuides: List<Guide>,
    val noSpoilGuides: List<Guide>,
){
    data class Guide(
        val title: String,
        val guideRateType: GuideRateType?,
        val guides: List<String>,
    )
}
