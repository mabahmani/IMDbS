package com.mabahmani.domain.vo.common

data class Name(
    val nameId: NameId,
    val name: String,
    val avatar: Image,
    val summary: String,
    val topRole: String,
    val topTitle: TitleLink
)