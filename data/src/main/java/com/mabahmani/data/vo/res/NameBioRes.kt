package com.mabahmani.data.vo.res

import com.mabahmani.domain.vo.NameBio
import com.mabahmani.domain.vo.common.Id
import com.mabahmani.domain.vo.common.Image
import com.mabahmani.domain.vo.common.TextLink
import com.mabahmani.domain.vo.common.TitleId

data class NameBioRes(
    val avatar: String?,
    val family: List<Family>?,
    val miniBio: String?,
    val name: String?,
    val overview: List<Overview>?,
    val salary: List<Salary>?,
    val trademark: List<String>?,
    val trivia: List<String>?
) {
    data class Family(
        val id: String?,
        val subtitle: String?,
        val title: String?
    )

    data class Overview(
        val id: String?,
        val subtitle: String?,
        val title: String?
    )

    data class Salary(
        val id: String?,
        val subtitle: String?,
        val title: String?
    )

    fun toNameBio(): NameBio{
        return NameBio(
            name.orEmpty(),
            Image(avatar.orEmpty()),
            overview?.map { TextLink(it.id.orEmpty(),it.title.orEmpty(),it.subtitle.orEmpty(),"") }?: listOf(),
            miniBio.orEmpty(),
            family?.map { TextLink(it.id.orEmpty(),it.title.orEmpty(),it.subtitle.orEmpty(),"") }?: listOf(),
            trademark.orEmpty(),
            trivia.orEmpty(),
            salary?.map { TextLink(it.id.orEmpty(),it.title.orEmpty(),it.subtitle.orEmpty(),"") }?: listOf(),
        )
    }
}