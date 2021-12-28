package com.mabahmani.data.ds

import com.mabahmani.data.vo.generic.ApiResponse
import com.mabahmani.data.vo.res.*

interface RemoteDataSource {

    suspend fun getChartBottom100(): Result<ApiResponse<List<ChartRes>>>

    suspend fun getChartBoxOffice(): Result<ApiResponse<ChartBoxOfficeRes>>

    suspend fun getChartPopular(): Result<ApiResponse<List<ChartRes>>>

    suspend fun getChartTop250(): Result<ApiResponse<List<ChartRes>>>

    suspend fun getChartTvPopular(): Result<ApiResponse<List<ChartRes>>>

    suspend fun getChartTopTv250(): Result<ApiResponse<List<ChartRes>>>

    suspend fun getEvents(): Result<ApiResponse<List<EventRes>>>

    suspend fun getEventDetails(
        eventId: String,
        year: String
    ): Result<ApiResponse<EventDetailsRes>>

    suspend fun getGenres(): Result<ApiResponse<List<GenresRes>>>

    suspend fun getHome(): Result<ApiResponse<HomeRes>>

    suspend fun getHomeExtra(): Result<ApiResponse<HomeExtraRes>>

    suspend fun getListImages(listId: String): Result<ApiResponse<ImagesRes>>

    suspend fun getListImagesWithDetails(
        listId: String,
        after: String,
        before: String,
        first: Int,
        last: Int,
        imageId: String,
    ): Result<ApiResponse<ImageDetailsRes>>

    suspend fun getNameImages(
        nameId: String,
        page: String
    ): Result<ApiResponse<ImagesRes>>

    suspend fun getNameImagesWithDetails(
        nameId: String,
        after: String,
        before: String,
        first: Int,
        last: Int,
        imageId: String,
    ): Result<ApiResponse<ImageDetailsRes>>

    suspend fun getTitleImages(
        titleId: String,
        page: String
    ): Result<ApiResponse<ImagesRes>>

    suspend fun getTitleImagesWithDetails(
        titleId: String,
        after: String,
        before: String,
        first: Int,
        last: Int,
        imageId: String,
    ): Result<ApiResponse<ImageDetailsRes>>

    suspend fun getKeywords(): Result<ApiResponse<List<GenresRes>>>

    suspend fun getNameDetails(nameId: String): Result<ApiResponse<NameDetailsRes>>

    suspend fun getNameAwards(nameId: String): Result<ApiResponse<NameAwardsRes>>

    suspend fun getNameBio(nameId: String): Result<ApiResponse<NameBioRes>>

    suspend fun getNewsDetails(newsId: String): Result<ApiResponse<NewsDetailsRes>>

    suspend fun searchNames(
        bio: String,
        birthDate: String,
        birthMonthDay: String,
        birthPlace: String,
        deathDate: String,
        deathPlace: String,
        gender: String,
        groups: String,
        name: String,
        roles: String,
        sort: String,
        starSign: String,
        start: String,
    ): Result<ApiResponse<List<SearchNameRes>>>

    suspend fun searchTitles(
        certificates: String,
        colors: String,
        companies: String,
        countries: String,
        genres: String,
        groups: String,
        keywords: String,
        languages: String,
        locations: String,
        plot: String,
        releaseDate: String,
        role: String,
        runtime: String,
        sort: String,
        start: String,
        title: String,
        titleType: String,
        userRating: String
    ): Result<ApiResponse<List<SearchNameRes>>>

    suspend fun getTitleDetails(titleId: String): Result<ApiResponse<TitleDetailsRes>>

    suspend fun getTitleAwards(titleId: String): Result<ApiResponse<TitleAwardsRes>>

    suspend fun getTitleFullCredits(titleId: String): Result<ApiResponse<TitleFullCreditsRes>>

    suspend fun getTitleParentalGuide(titleId: String): Result<ApiResponse<TitleParentsGuideRes>>

    suspend fun getTitleTechnicalSpecs(titleId: String): Result<ApiResponse<TitleTechnicalSpecsRes>>

    suspend fun getTitlesCalender(): Result<ApiResponse<CalenderRes>>

    suspend fun getTrailersAnticipated(): Result<ApiResponse<List<TrailerRes>>>

    suspend fun getTrailersPopular(): Result<ApiResponse<List<TrailerRes>>>

    suspend fun getTrailersRecent(): Result<ApiResponse<List<TrailerRes>>>

    suspend fun getTrailersTrending(): Result<ApiResponse<List<TrailerRes>>>

    suspend fun getVideoDetails(videoId: String): Result<ApiResponse<VideoDetailsRes>>

    suspend fun getNameVideos(
        nameId: String,
        page: String,
    ): Result<ApiResponse<VideosRes>>

    suspend fun getTitleVideos(
        titleId: String,
        page: String,
    ): Result<ApiResponse<VideosRes>>

    suspend fun suggestAll(
        firstLetter: String,
        term: String,
    ): Result<ApiResponse<SuggestRes>>

    suspend fun suggestTitles(
        firstLetter: String,
        term: String,
    ): Result<ApiResponse<SuggestRes>>

    suspend fun suggestNames(
        firstLetter: String,
        term: String,
    ): Result<ApiResponse<SuggestRes>>
}