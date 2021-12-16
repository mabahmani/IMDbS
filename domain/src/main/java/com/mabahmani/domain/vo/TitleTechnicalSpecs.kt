package com.mabahmani.domain.vo

import com.mabahmani.domain.vo.common.Image
import com.mabahmani.domain.vo.common.Text

data class TitleTechnicalSpecs(
    val name: String,
    val year: String,
    val cover: Image,
    val items: List<Text>
)
