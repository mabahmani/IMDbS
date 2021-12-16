package com.mabahmani.domain.vo

import com.mabahmani.domain.vo.common.*

data class NameDetails(
    val nameId: NameId,
    val name: String,
    val jobTitles: List<String>,
    val avatar: Image,
    val trailer: Video,
    val bioSummary: String,
    val birthDate: String,
    val birthDateMonthDay: String,
    val birthPlace: String,
    val photos: List<ImageLink>,
    val knownForTitles: List<Title>,
    val filmographies: List<Filmography>,
    val relatedVideos: List<Video>,
    val personalDetails: List<PersonalDetails>
)

{
    data class Filmography(
        val title:String,
        val credits: List<Credit>
    )
    {
        data class Credit(
            val titleId: TitleId,
            val title: String,
            val subtitle: String,
            val year: String,
            val episodes: List<Episode>
        )
        {
            data class Episode(
                val titleId: TitleId,
                val title: String,
                val subtitle: String,
            )
        }
    }


    data class PersonalDetails(
        val title: String,
        val subtitle: String,
        val links: List<TextLink>
    )
}
