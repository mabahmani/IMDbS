package com.mabahmani.data.vo.res

import com.mabahmani.domain.vo.NameDetails
import com.mabahmani.domain.vo.common.*

data class NameDetailsRes(
    val avatar: String?,
    val bioSummary: String?,
    val birthDate: String?,
    val birthDateMonthDay: String?,
    val birthDateYear: String?,
    val birthPlace: String?,
    val filmographies: List<Filmography>?,
    val id: String?,
    val jobTitles: List<String>?,
    val knownForList: List<KnownFor>?,
    val name: String?,
    val personalDetails: List<PersonalDetail>?,
    val photos: List<Photo>?,
    val relatedVideos: List<RelatedVideo>?,
    val trailer: Trailer?
) {
    data class Filmography(
        val credits: List<Credit>?,
        val headTitle: String?,
        val numberOfCredits: String?
    ) {
        data class Credit(
            val episodes: List<Episode>?,
            val title: String?,
            val id: String?,
            val subtitle: String?,
            val year: String?,

            ) {
            data class Episode(
                val title: String?,
                val id: String?,
                val subtitle: String?
            )
        }
    }

    data class KnownFor(
        val cover: String?,
        val inMovieName: String?,
        val title: String?,
        val titleId: String?,
        val year: String?
    )

    data class PersonalDetail(
        val linkTexts: List<LinkText>?,
        val subtitle: String?,
        val title: String?
    ) {
        data class LinkText(
            val text: String?,
            val url: String?
        )
    }

    data class Photo(
        val id: String?,
        val url: String?
    )

    data class RelatedVideo(
        val cover: String?,
        val title: String?,
        val videoId: String?
    )

    data class Trailer(
        val videoId: String?,
        val cover: String?,
        val caption: String?
    )

    fun toNameDetails(): NameDetails {
        return NameDetails(
            NameId(id.orEmpty()),
            name.orEmpty(),
            jobTitles ?: listOf(),
            Image(avatar.orEmpty()),
            Video(VideoId(trailer?.videoId.orEmpty()), Image(trailer?.cover.orEmpty()), trailer?.caption.orEmpty(), ""),
            bioSummary.orEmpty(),
            birthDateYear.orEmpty(),
            birthDateMonthDay.orEmpty(),
            birthPlace.orEmpty(),
            photos?.map { ImageLink(Image(it.url.orEmpty()), ImageId(it.id.orEmpty())) } ?: listOf(),
            knownForList?.map {
                Title(
                    Image(it.cover.orEmpty()),
                    null,
                    null,
                    null,
                    null,
                    null,
                    it.title.orEmpty(),
                    it.year.orEmpty(),
                    null,
                    null,
                    null,
                    it.inMovieName.orEmpty(),
                    null,
                    null,
                    TitleId(it.titleId.orEmpty()),
                    null,
                    null,
                    null,
                    null
                )
            } ?: listOf(),
            filmographies?.map {
                NameDetails.Filmography(it.headTitle.orEmpty(),
                    it.credits?.map {
                        NameDetails.Filmography.Credit(
                            TitleId(it.id.orEmpty()),
                            it.title.orEmpty(),
                            it.subtitle.orEmpty(),
                            it.year.orEmpty(),
                            it.episodes?.map {
                                NameDetails.Filmography.Credit.Episode(
                                    TitleId(it.id.orEmpty()),
                                    it.title.orEmpty(),
                                    it.subtitle.orEmpty()
                                )
                            }?: listOf()
                        )
                    }?: listOf()
                )
            }?: listOf(),
            relatedVideos?.map {
                Video(VideoId(it.videoId.orEmpty()), Image(it.cover.orEmpty()),it.title.orEmpty(),"")
            }?: listOf(),
            personalDetails?.map { NameDetails.PersonalDetails(
                it.title.orEmpty(),
                it.subtitle?.replace("»", "").orEmpty(),
                it.linkTexts?.map { TextLink("",it.text.orEmpty(),"",it.url.orEmpty()) }?: listOf()
            ) }?: listOf()
        )
    }
}