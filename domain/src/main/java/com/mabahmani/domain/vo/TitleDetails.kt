package com.mabahmani.domain.vo

import com.mabahmani.domain.vo.common.*

data class TitleDetails(
    val name: String,
    val releaseYear: String,
    val certificate: String,
    val runtime: String,
    val imdbRating: String,
    val numberOfVotes: String,
    val cover: Image,
    val trailer: Video,
    val genres: List<String>,
    val plot: String,
    val directors: List<NameLink>,
    val writers: List<NameLink>,
    val stars: List<NameLink>,
    val videos: List<Video>,
    val photos: List<ImageLink>,
    val topCasts: List<Cast>,
    val relatedTitles: List<Title>,
    val storyLine: StoryLine,
    val topReview: Review,
    val details: Detail,
    val boxOffice: BoxOffice,
    val technicalSpecs: List<Text>
    ) {

    data class StoryLine(
        val story: String,
        val keywords: List<String>,
        val genres: List<String>,
        val taglines: String,
        val motionPictureRating: String,
    )

    data class Review(
        val title:String,
        val review:String,
        val date:String,
        val rate:String,
        val username:String,
    )

    data class Detail(
        val releaseDate: List<String>,
        val countryOfOrigin: List<String>,
        val officialSites: List<String>,
        val languages: List<String>,
        val filmingLocations: List<String>,
        val productionCompanies: List<String>,
    )

    data class BoxOffice(
        val budget: String,
        val grossUsAndCanada: String,
        val openingWeekendUsAndCanada: String,
        val grossWorldwide: String,
    )
}