package com.mabahmani.data.remote

import com.mabahmani.data.vo.generic.ApiResponse
import com.mabahmani.data.vo.res.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ImdbScrapingApiService {

    @GET("chart/bottom100")
    suspend fun getChartBottom100(): Response<ApiResponse<List<ChartRes>>>

    @GET("chart/boxoffice")
    suspend fun getChartBoxOffice(): Response<ApiResponse<ChartBoxOfficeRes>>

    @GET("chart/popular")
    suspend fun getChartPopular(): Response<ApiResponse<List<ChartRes>>>

    @GET("chart/top250")
    suspend fun getChartTop250(): Response<ApiResponse<List<ChartRes>>>

    @GET("chart/tv/popular")
    suspend fun getChartTvPopular(): Response<ApiResponse<List<ChartRes>>>

    @GET("chart/tv/top250")
    suspend fun getChartTopTv250(): Response<ApiResponse<List<ChartRes>>>

    @GET("events/")
    suspend fun getEvents(): Response<ApiResponse<List<EventRes>>>

    @GET("events/{eventId}/{year}")
    suspend fun getEventDetails(
        @Path("eventId") eventId: String,
        @Path("year") year: String
    ): Response<ApiResponse<EventDetailsRes>>

    @GET("genres/")
    suspend fun getGenres(): Response<ApiResponse<List<GenresRes>>>

    @GET("home/")
    suspend fun getHome(): Response<ApiResponse<HomeRes>>

    @GET("home/extra")
    suspend fun getHomeExtra(): Response<ApiResponse<HomeExtraRes>>

    @GET("images/list/{listId}")
    suspend fun getListImages(@Path("listId") listId: String): Response<ApiResponse<ImagesRes>>

    @GET("images/list/{listId}/slider")
    suspend fun getListImagesWithDetails(
        @Path("listId") listId: String,
        @Query("after") after: String?,
        @Query("before") before: String?,
        @Query("first") first: Int?,
        @Query("last") last: Int?,
        @Query("imageId") imageId: String?,
    ): Response<ApiResponse<ImageDetailsRes>>

    @GET("images/names/{nameId}")
    suspend fun getNameImages(
        @Path("nameId") nameId: String,
        @Query("page") page: String
    ): Response<ApiResponse<ImagesRes>>

    @GET("images/names/{nameId}/slider")
    suspend fun getNameImagesWithDetails(
        @Path("nameId") nameId: String,
        @Query("after") after: String?,
        @Query("before") before: String?,
        @Query("first") first: Int?,
        @Query("last") last: Int?,
        @Query("imageId") imageId: String?,
    ): Response<ApiResponse<ImageDetailsRes>>

    @GET("images/titles/{titleId}")
    suspend fun getTitleImages(
        @Path("titleId") titleId: String,
        @Query("page") page: String
    ): Response<ApiResponse<ImagesRes>>

    @GET("images/titles/{titleId}/slider")
    suspend fun getTitleImagesWithDetails(
        @Path("titleId") titleId: String,
        @Query("after") after: String?,
        @Query("before") before: String?,
        @Query("first") first: Int?,
        @Query("last") last: Int?,
        @Query("imageId") imageId: String?,
    ): Response<ApiResponse<ImageDetailsRes>>

    @GET("keywords/")
    suspend fun getKeywords(): Response<ApiResponse<List<String>>>

    @GET("names/{nameId}")
    suspend fun getNameDetails(@Path("nameId") nameId: String): Response<ApiResponse<NameDetailsRes>>

    @GET("names/{nameId}/awards")
    suspend fun getNameAwards(@Path("nameId") nameId: String): Response<ApiResponse<NameAwardsRes>>

    @GET("names/{nameId}/bio")
    suspend fun getNameBio(@Path("nameId") nameId: String): Response<ApiResponse<NameBioRes>>

    @GET("news/{newsId}")
    suspend fun getNewsDetails(@Path("newsId") newsId: String): Response<ApiResponse<NewsDetailsRes>>

    @GET("search/names")
    suspend fun searchNames(
        @Query("bio") bio: String?,
        @Query("birthDate") birthDate: String?,
        @Query("birthMonthDay") birthMonthDay: String?,
        @Query("birthPlace") birthPlace: String?,
        @Query("deathDate") deathDate: String?,
        @Query("deathPlace") deathPlace: String?,
        @Query("gender") gender: String?,
        @Query("groups") groups: String?,
        @Query("name") name: String?,
        @Query("roles") roles: String?,
        @Query("sort") sort: String?,
        @Query("starSign") starSign: String?,
        @Query("start") start: String?,
    ): Response<ApiResponse<List<SearchNameRes>>>

    @GET("search/titles")
    suspend fun searchTitles(
        @Query("certificates") certificates: String?,
        @Query("colors") colors: String?,
        @Query("companies") companies: String?,
        @Query("countries") countries: String?,
        @Query("genres") genres: String?,
        @Query("groups") groups: String?,
        @Query("keywords") keywords: String?,
        @Query("languages") languages: String?,
        @Query("locations") locations: String?,
        @Query("plot") plot: String?,
        @Query("releaseDate") releaseDate: String?,
        @Query("role") role: String?,
        @Query("runtime") runtime: String?,
        @Query("sort") sort: String?,
        @Query("start") start: String?,
        @Query("title") title: String?,
        @Query("titleType") titleType: String?,
        @Query("userRating") userRating: String?
    ): Response<ApiResponse<List<SearchTitlesRes>>>

    @GET("titles/{titleId}")
    suspend fun getTitleDetails(@Path("titleId") titleId: String): Response<ApiResponse<TitleDetailsRes>>

    @GET("titles/{titleId}/awards")
    suspend fun getTitleAwards(@Path("titleId") titleId: String): Response<ApiResponse<TitleAwardsRes>>

    @GET("titles/{titleId}/fullcredits")
    suspend fun getTitleFullCredits(@Path("titleId") titleId: String): Response<ApiResponse<TitleFullCreditsRes>>

    @GET("titles/{titleId}/parentalguide")
    suspend fun getTitleParentalGuide(@Path("titleId") titleId: String): Response<ApiResponse<TitleParentsGuideRes>>

    @GET("titles/{titleId}/technical")
    suspend fun getTitleTechnicalSpecs(@Path("titleId") titleId: String): Response<ApiResponse<TitleTechnicalSpecsRes>>

    @GET("titles/calender")
    suspend fun getTitlesCalender(): Response<ApiResponse<List<CalenderRes>>>

    @GET("trailers/anticipated")
    suspend fun getTrailersAnticipated(): Response<ApiResponse<List<TrailerRes>>>

    @GET("trailers/popular")
    suspend fun getTrailersPopular(): Response<ApiResponse<List<TrailerRes>>>

    @GET("trailers/recent")
    suspend fun getTrailersRecent(): Response<ApiResponse<List<TrailerRes>>>

    @GET("trailers/trending")
    suspend fun getTrailersTrending(): Response<ApiResponse<List<TrailerRes>>>

    @GET("videos/{videoId}")
    suspend fun getVideoDetails(@Path("videoId") videoId: String): Response<ApiResponse<VideoDetailsRes>>

    @GET("videos/name/{nameId}/videogallery")
    suspend fun getNameVideos(
        @Path("nameId") nameId: String,
        @Query("page") page: String,
    ): Response<ApiResponse<VideosRes>>

    @GET("videos/title/{titleId}/videogallery")
    suspend fun getTitleVideos(
        @Path("titleId") titleId: String,
        @Query("page") page: String,
    ): Response<ApiResponse<VideosRes>>


}