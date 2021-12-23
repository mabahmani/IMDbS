package com.mabahmani.data.vo.res

import com.mabahmani.domain.vo.TitleDetails
import com.mabahmani.domain.vo.common.*

data class TitleDetailsRes(
    val boxOffice: BoxOffice,
    val details: Details,
    val overview: Overview,
    val photos: List<Photo>,
    val relatedMovies: List<RelatedMovie>,
    val storyline: Storyline,
    val technicalSpecs: List<TechnicalSpec>,
    val topCasts: List<TopCast>,
    val topReview: TopReview,
    val videos: List<Video>
) {
    data class BoxOffice(
        val budget: String,
        val grossUsAndCanada: String,
        val grossWorldwide: String,
        val openingWeekendUsAndCanada: String
    )

    data class Details(
        val countryOfOrigin: List<CountryOfOrigin>,
        val filmingLocations: List<FilmingLocation>,
        val language: List<Language>,
        val officialSites: List<OfficialSite>,
        val productionCompanies: List<ProductionCompany>,
        val releaseDate: List<ReleaseDate>
    ) {
        data class CountryOfOrigin(
            val link: String,
            val title: String
        )

        data class FilmingLocation(
            val link: String,
            val title: String
        )

        data class Language(
            val link: String,
            val title: String
        )

        data class OfficialSite(
            val link: String,
            val title: String
        )

        data class ProductionCompany(
            val link: String,
            val title: String
        )

        data class ReleaseDate(
            val link: String,
            val title: String
        )
    }

    data class Overview(
        val cover: String,
        val directors: List<Director>,
        val genres: List<Genre>,
        val imdbRating: String,
        val numberOfRate: String,
        val parentalGuidCertificate: String,
        val plot: String,
        val releaseYear: String,
        val runtime: String,
        val stars: List<Star>,
        val title: String,
        val trailerDuration: String,
        val trailerPreview: String,
        val trailerVideoId: String,
        val writers: List<Writer>
    ) {
        data class Director(
            val id: String,
            val image: Any,
            val link: String,
            val movieName: Any,
            val realName: String
        )

        data class Genre(
            val link: String,
            val title: String
        )

        data class Star(
            val id: String,
            val image: Any,
            val link: String,
            val movieName: Any,
            val realName: String
        )

        data class Writer(
            val id: String,
            val image: Any,
            val link: String,
            val movieName: Any,
            val realName: String
        )
    }

    data class Photo(
        val original: String,
        val thumbnail: String
    )

    data class RelatedMovie(
        val cover: String,
        val id: String,
        val link: String,
        val rate: String,
        val title: String
    )

    data class Storyline(
        val genres: List<Genre>,
        val keywords: List<Keyword>,
        val motionPictureRating: MotionPictureRating,
        val parentsGuide: ParentsGuide,
        val story: String,
        val taglines: String
    ) {
        data class Genre(
            val link: String,
            val title: String
        )

        data class Keyword(
            val link: String,
            val title: String
        )

        data class MotionPictureRating(
            val link: String,
            val title: String
        )

        data class ParentsGuide(
            val link: String,
            val title: Any
        )
    }

    data class TechnicalSpec(
        val subtitle: String,
        val title: String
    )

    data class TopCast(
        val id: String,
        val image: String,
        val link: String,
        val movieName: String,
        val realName: String
    )

    data class TopReview(
        val date: String,
        val rating: String,
        val review: String,
        val title: String,
        val username: String
    )

    data class Video(
        val duration: String,
        val id: String,
        val link: String,
        val preview: String,
        val title: String
    )

    fun toTitleDetails(): TitleDetails {
        return TitleDetails(
            overview.title,
            overview.releaseYear,
            overview.parentalGuidCertificate,
            overview.runtime,
            overview.imdbRating,
            overview.numberOfRate,
            Image(overview.cover),
            Video(VideoId(overview.trailerVideoId), Image(overview.trailerPreview), "", overview.runtime),
            overview.genres.map { it.title },
            overview.plot,
            overview.directors.map { NameLink(it.realName, NameId(it.id)) },
            overview.writers.map { NameLink(it.realName, NameId(it.id)) },
            overview.stars.map { NameLink(it.realName, NameId(it.id)) },
            videos.map { Video(VideoId(it.id), Image(it.preview), it.title, it.duration) },
            photos.map { ImageLink(Image(it.original),null) },
            topCasts.map { Cast(it.realName,it.movieName,Image(it.image),NameId(it.id)) },
            relatedMovies.map { Title(Image(it.cover), it.rate, null, null, null, null, it.title, null, null, null, null, null, null, null,TitleId(it.id), null) },
            TitleDetails.StoryLine(storyline.story,storyline.keywords.map { it.title },storyline.genres.map { it.title },storyline.taglines,storyline.motionPictureRating.title),
            TitleDetails.Review(topReview.title,topReview.review,topReview.date,topReview.rating,topReview.username),
            TitleDetails.Detail(details.releaseDate.map { it.title },details.countryOfOrigin.map { it.title },details.officialSites.map { it.title },details.language.map { it.title },details.filmingLocations.map { it.title },details.productionCompanies.map { it.title }),
            TitleDetails.BoxOffice(boxOffice.budget,boxOffice.grossUsAndCanada,boxOffice.openingWeekendUsAndCanada,boxOffice.grossWorldwide),
            technicalSpecs.map { Text(it.title,it.subtitle) }

        )
    }
}