package com.mabahmani.data.ds

import com.mabahmani.data.di.IoDispatcher
import com.mabahmani.data.remote.ImdbScrapingApiService
import com.mabahmani.data.remote.ImdbSuggestionApiService
import com.mabahmani.data.remote.safeApiCall
import com.mabahmani.data.vo.generic.ApiResponse
import com.mabahmani.data.vo.res.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val okHttpClient: OkHttpClient,
    private val imdbScrapingApiService: ImdbScrapingApiService,
    private val imdbSuggestionApiService: ImdbSuggestionApiService
    ): RemoteDataSource {


    override suspend fun getChartBottom100(): Result<ApiResponse<List<ChartRes>>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbScrapingApiService.getChartBottom100()
            }
        }
    }

    override suspend fun getChartBoxOffice(): Result<ApiResponse<ChartBoxOfficeRes>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbScrapingApiService.getChartBoxOffice()
            }
        }
    }

    override suspend fun getChartPopular(): Result<ApiResponse<List<ChartRes>>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient){
                imdbScrapingApiService.getChartPopular()
            }
        }
    }

    override suspend fun getChartTop250(): Result<ApiResponse<List<ChartRes>>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient){
                imdbScrapingApiService.getChartTop250()
            }
        }
    }

    override suspend fun getChartTvPopular(): Result<ApiResponse<List<ChartRes>>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient){
                imdbScrapingApiService.getChartTvPopular()
            }
        }
    }

    override suspend fun getChartTopTv250(): Result<ApiResponse<List<ChartRes>>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbScrapingApiService.getChartTopTv250()
            }
        }
    }

    override suspend fun getEvents(): Result<ApiResponse<List<EventRes>>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbScrapingApiService.getEvents()
            }
        }
    }

    override suspend fun getEventDetails(
        eventId: String,
        year: String
    ): Result<ApiResponse<EventDetailsRes>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbScrapingApiService.getEventDetails(eventId, year)
            }
        }
    }

    override suspend fun getGenres(): Result<ApiResponse<List<GenresRes>>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbScrapingApiService.getGenres()
            }
        }
    }

    override suspend fun getHome(): Result<ApiResponse<HomeRes>> {

        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbScrapingApiService.getHome()
            }
        }
    }

    override suspend fun getHomeExtra(): Result<ApiResponse<HomeExtraRes>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbScrapingApiService.getHomeExtra()
            }
        }
    }

    override suspend fun getListImages(listId: String): Result<ApiResponse<ImagesRes>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbScrapingApiService.getListImages(listId)
            }
        }
    }

    override suspend fun getListImagesWithDetails(
        listId: String,
        after: String?,
        before: String?,
        first: Int?,
        last: Int?,
        imageId: String?
    ): Result<ApiResponse<ImageDetailsRes>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbScrapingApiService.getListImagesWithDetails(
                    listId,
                    after,
                    before,
                    first,
                    last,
                    imageId
                )
            }
        }
    }

    override suspend fun getNameImages(
        nameId: String,
        page: String
    ): Result<ApiResponse<ImagesRes>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbScrapingApiService.getNameImages(nameId, page)
            }
        }
    }

    override suspend fun getGalleryImages(
        galleryId: String,
        page: String
    ): Result<ApiResponse<ImagesRes>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbScrapingApiService.getGalleryImages(galleryId, page)
            }
        }
    }

    override suspend fun getNameImagesWithDetails(
        nameId: String,
        after: String?,
        before: String?,
        first: Int?,
        last: Int?,
        imageId: String?
    ): Result<ApiResponse<ImageDetailsRes>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbScrapingApiService.getNameImagesWithDetails(
                    nameId,
                    after,
                    before,
                    first,
                    last,
                    imageId
                )
            }
        }
    }

    override suspend fun getGalleryImagesWithDetails(
        galleryId: String,
        after: String?,
        before: String?,
        first: Int?,
        last: Int?,
        imageId: String?
    ): Result<ApiResponse<ImageDetailsRes>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbScrapingApiService.getGalleryImagesWithDetails(
                    galleryId,
                    after,
                    before,
                    first,
                    last,
                    imageId
                )
            }
        }
    }

    override suspend fun getTitleImages(
        titleId: String,
        page: String
    ): Result<ApiResponse<ImagesRes>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbScrapingApiService.getTitleImages(titleId, page)
            }
        }
    }

    override suspend fun getTitleImagesWithDetails(
        titleId: String,
        after: String?,
        before: String?,
        first: Int?,
        last: Int?,
        imageId: String?
    ): Result<ApiResponse<ImageDetailsRes>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbScrapingApiService.getTitleImagesWithDetails(
                    titleId,
                    after,
                    before,
                    first,
                    last,
                    imageId
                )
            }
        }
    }

    override suspend fun getKeywords(): Result<ApiResponse<List<String>>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbScrapingApiService.getKeywords()
            }
        }
    }

    override suspend fun getNameDetails(nameId: String): Result<ApiResponse<NameDetailsRes>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbScrapingApiService.getNameDetails(nameId)
            }
        }
    }

    override suspend fun getNameAwards(nameId: String): Result<ApiResponse<NameAwardsRes>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbScrapingApiService.getNameAwards(nameId)
            }
        }
    }

    override suspend fun getNameBio(nameId: String): Result<ApiResponse<NameBioRes>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbScrapingApiService.getNameBio(nameId)
            }
        }
    }

    override suspend fun getNewsDetails(newsId: String): Result<ApiResponse<NewsDetailsRes>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbScrapingApiService.getNewsDetails(newsId)
            }
        }
    }

    override suspend fun searchNames(
        bio: String?,
        birthDate: String?,
        birthMonthDay: String?,
        birthPlace: String?,
        deathDate: String?,
        deathPlace: String?,
        gender: String?,
        groups: String?,
        name: String?,
        roles: String?,
        sort: String?,
        starSign: String?,
        start: String?
    ): Result<ApiResponse<List<SearchNameRes>>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbScrapingApiService.searchNames(
                    bio, birthDate, birthMonthDay, birthPlace, deathDate, deathPlace, gender, groups, name, roles, sort, starSign, start
                )
            }
        }
    }

    override suspend fun searchTitles(
        certificates: String?,
        colors: String?,
        companies: String?,
        countries: String?,
        genres: String?,
        groups: String?,
        keywords: String?,
        languages: String?,
        locations: String?,
        plot: String?,
        releaseDate: String?,
        role: String?,
        runtime: String?,
        sort: String?,
        start: String?,
        title: String?,
        titleType: String?,
        userRating: String?
    ): Result<ApiResponse<List<SearchTitlesRes>>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbScrapingApiService.searchTitles(certificates, colors, companies, countries, genres, groups, keywords, languages, locations, plot, releaseDate, role, runtime, sort, start, title, titleType, userRating)
            }
        }
    }

    override suspend fun getTitleDetails(titleId: String): Result<ApiResponse<TitleDetailsRes>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbScrapingApiService.getTitleDetails(titleId)
            }
        }
    }

    override suspend fun getTitleAwards(titleId: String): Result<ApiResponse<TitleAwardsRes>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbScrapingApiService.getTitleAwards(titleId)
            }
        }
    }

    override suspend fun getTitleFullCredits(titleId: String): Result<ApiResponse<TitleFullCreditsRes>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbScrapingApiService.getTitleFullCredits(titleId)
            }
        }
    }

    override suspend fun getTitleParentalGuide(titleId: String): Result<ApiResponse<TitleParentsGuideRes>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbScrapingApiService.getTitleParentalGuide(titleId)
            }
        }
    }

    override suspend fun getTitleTechnicalSpecs(titleId: String): Result<ApiResponse<TitleTechnicalSpecsRes>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbScrapingApiService.getTitleTechnicalSpecs(titleId)
            }
        }
    }

    override suspend fun getTitlesCalender(): Result<ApiResponse<List<CalenderRes>>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbScrapingApiService.getTitlesCalender()
            }
        }
    }

    override suspend fun getTrailersAnticipated(): Result<ApiResponse<List<TrailerRes>>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbScrapingApiService.getTrailersAnticipated()
            }
        }
    }

    override suspend fun getTrailersPopular(): Result<ApiResponse<List<TrailerRes>>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbScrapingApiService.getTrailersPopular()
            }
        }
    }

    override suspend fun getTrailersRecent(): Result<ApiResponse<List<TrailerRes>>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbScrapingApiService.getTrailersRecent()
            }
        }
    }

    override suspend fun getTrailersTrending(): Result<ApiResponse<List<TrailerRes>>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbScrapingApiService.getTrailersTrending()
            }
        }
    }

    override suspend fun getVideoDetails(videoId: String): Result<ApiResponse<VideoDetailsRes>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbScrapingApiService.getVideoDetails(videoId)
            }
        }
    }

    override suspend fun getNameVideos(
        nameId: String,
        page: String
    ): Result<ApiResponse<VideosRes>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbScrapingApiService.getNameVideos(nameId, page)
            }
        }
    }

    override suspend fun getTitleVideos(
        titleId: String,
        page: String
    ): Result<ApiResponse<VideosRes>> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbScrapingApiService.getTitleVideos(titleId, page)
            }
        }
    }

    override suspend fun suggestAll(
        firstLetter: String,
        term: String
    ): Result<SuggestRes> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
               imdbSuggestionApiService.suggestAll(firstLetter,term)
            }
        }
    }

    override suspend fun suggestTitles(
        firstLetter: String,
        term: String
    ): Result<SuggestRes> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbSuggestionApiService.suggestTitles(firstLetter,term)
            }
        }    }

    override suspend fun suggestNames(
        firstLetter: String,
        term: String
    ): Result<SuggestRes> {
        return withContext(ioDispatcher){
            safeApiCall(okHttpClient) {
                imdbSuggestionApiService.suggestNames(firstLetter,term)
            }
        }
    }
}