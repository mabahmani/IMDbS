package com.mabahmani.domain.vo

import com.mabahmani.domain.vo.common.Image
import com.mabahmani.domain.vo.common.TextLink

data class NameBio(
    val name: String,
    val avatar: Image,
    val overview: List<TextLink>,
    val miniBio: String,
    val family: List<TextLink>,
    val trademark: List<String>,
    val trivia: List<String>,
    val salary: List<TextLink>,
)
