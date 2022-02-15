package com.mabahmani.data.vo.res

import com.mabahmani.domain.vo.TitleDetails
import com.mabahmani.domain.vo.common.*

data class TitleDetailsRes(
    val boxOffice: BoxOffice?,
    val details: Details?,
    val overview: Overview?,
    val photos: List<Photo>?,
    val relatedMovies: List<RelatedMovie>?,
    val storyline: Storyline?,
    val technicalSpecs: List<TechnicalSpec>?,
    val topCasts: List<TopCast>?,
    val topReview: TopReview?,
    val videos: List<Video>?
) {
    data class BoxOffice(
        val budget: String?,
        val grossUsAndCanada: String?,
        val grossWorldwide: String?,
        val openingWeekendUsAndCanada: String?
    )

    data class Details(
        val countryOfOrigin: List<CountryOfOrigin>?,
        val filmingLocations: List<FilmingLocation>?,
        val language: List<Language>?,
        val officialSites: List<OfficialSite>?,
        val productionCompanies: List<ProductionCompany>?,
        val releaseDate: List<ReleaseDate>?
    ) {
        data class CountryOfOrigin(
            val link: String?,
            val title: String?
        )

        data class FilmingLocation(
            val link: String?,
            val title: String?
        )

        data class Language(
            val link: String?,
            val title: String?
        )

        data class OfficialSite(
            val link: String?,
            val title: String?
        )

        data class ProductionCompany(
            val link: String?,
            val title: String?
        )

        data class ReleaseDate(
            val link: String?,
            val title: String?
        )
    }

    data class Overview(
        val cover: String?,
        val directors: List<Director>?,
        val genres: List<Genre>?,
        val imdbRating: String?,
        val numberOfRate: String?,
        val parentalGuidCertificate: String?,
        val plot: String?,
        val releaseYear: String?,
        val runtime: String?,
        val stars: List<Star>?,
        val title: String?,
        val trailerDuration: String?,
        val trailerPreview: String?,
        val trailerVideoId: String?,
        val writers: List<Writer>?
    ) {
        data class Director(
            val id: String?,
            val image: String?,
            val link: String?,
            val movieName: String?,
            val realName: String?
        )

        data class Genre(
            val link: String?,
            val title: String?
        )

        data class Star(
            val id: String?,
            val image: String?,
            val link: String?,
            val movieName: String?,
            val realName: String?
        )

        data class Writer(
            val id: String?,
            val image: String?,
            val link: String?,
            val movieName: String?,
            val realName: String?
        )
    }

    data class Photo(
        val original: String?,
        val thumbnail: String?
    )

    data class RelatedMovie(
        val cover: String?,
        val id: String?,
        val link: String?,
        val rate: String?,
        val title: String?
    )

    data class Storyline(
        val genres: List<Genre>?,
        val keywords: List<Keyword>?,
        val motionPictureRating: MotionPictureRating?,
        val parentsGuide: ParentsGuide?,
        val story: String?,
        val taglines: String?
    ) {
        data class Genre(
            val link: String?,
            val title: String?
        )

        data class Keyword(
            val link: String?,
            val title: String?
        )

        data class MotionPictureRating(
            val link: String?,
            val title: String?
        )

        data class ParentsGuide(
            val link: String?,
            val title: String?
        )
    }

    data class TechnicalSpec(
        val subtitle: String?,
        val title: String?
    )

    data class TopCast(
        val id: String?,
        val image: String?,
        val link: String?,
        val movieName: String?,
        val realName: String?
    )

    data class TopReview(
        val date: String?,
        val rating: String?,
        val review: String?,
        val title: String?,
        val username: String?
    )

    data class Video(
        val duration: String?,
        val id: String?,
        val link: String?,
        val preview: String?,
        val title: String?
    )

    fun toTitleDetails(): TitleDetails {
        return TitleDetails(
            overview?.title.orEmpty(),
            overview?.releaseYear.orEmpty(),
            overview?.parentalGuidCertificate.orEmpty(),
            overview?.runtime.orEmpty(),
            overview?.imdbRating?.let { it.split("/")[0] }.toString(),
            overview?.numberOfRate.orEmpty(),
            Image(overview?.cover.orEmpty()),
            Video(VideoId(overview?.trailerVideoId.orEmpty()), Image(overview?.trailerPreview.orEmpty()), "", overview?.trailerDuration.orEmpty()),
            overview?.genres?.map { it.title.orEmpty() } ?: listOf(),
            overview?.plot.orEmpty(),
            overview?.directors?.map { NameLink(it.realName.orEmpty(), NameId(it.id.orEmpty())) } ?: listOf(),
            overview?.writers?.map { NameLink(it.realName.orEmpty(), NameId(it.id.orEmpty())) }?: listOf(),
            overview?.stars?.map { NameLink(it.realName.orEmpty(), NameId(it.id.orEmpty())) }?: listOf(),
            videos?.map { Video(VideoId(it.id.orEmpty()), Image(it.preview.orEmpty()), it.title.orEmpty(), it.duration.orEmpty()) }?: listOf(),
            photos?.map { ImageLink(Image(it.original.orEmpty()),null) }?: listOf(),
            topCasts?.map { Cast(it.realName.orEmpty(),it.movieName.orEmpty(),Image(it.image.orEmpty()),NameId(it.id.orEmpty())) }?: listOf(),
            relatedMovies?.map { Title(Image(it.cover.orEmpty()), it.rate.orEmpty(), null, null, null, null, it.title.orEmpty(), null, null, null, null, null, null, null,TitleId(it.id.orEmpty()), null,                    null,
                null,
                null) }?: listOf(),
            TitleDetails.StoryLine(storyline?.story.orEmpty(),storyline?.keywords?.map { it.title.orEmpty() }?: listOf(),storyline?.genres?.map { it.title.orEmpty() }?: listOf(),storyline?.taglines.orEmpty(),storyline?.motionPictureRating?.title.orEmpty()),
            TitleDetails.Review(topReview?.title.orEmpty(),topReview?.review.orEmpty(),topReview?.date.orEmpty(),topReview?.rating.orEmpty(),topReview?.username.orEmpty()),
            TitleDetails.Detail(details?.releaseDate?.map { it.title.orEmpty() }?: listOf(),details?.countryOfOrigin?.map { it.title.orEmpty() }?: listOf(),details?.officialSites?.map { it.title.orEmpty() }?: listOf(),details?.language?.map { it.title.orEmpty() }?: listOf(),details?.filmingLocations?.map { it.title.orEmpty() }?: listOf(),details?.productionCompanies?.map { it.title.orEmpty() }?: listOf()),
            TitleDetails.BoxOffice(boxOffice?.budget.orEmpty(),boxOffice?.grossUsAndCanada.orEmpty(),boxOffice?.openingWeekendUsAndCanada.orEmpty(),boxOffice?.grossWorldwide.orEmpty()),
            technicalSpecs?.map { Text(it.title.orEmpty(),it.subtitle.orEmpty()) }?: listOf()

        )
    }
}