package com.mabahmani.data.ds

import com.mabahmani.data.di.IoDispatcher
import com.mabahmani.data.remote.ImdbScrapingApiService
import com.mabahmani.data.remote.ImdbSuggestionApiService
import com.mabahmani.data.remote.safeApiCall
import com.mabahmani.data.vo.generic.ApiResponse
import com.mabahmani.data.vo.res.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val imdbScrapingApiService: ImdbScrapingApiService,
    private val imdbSuggestionApiService: ImdbSuggestionApiService
    ): RemoteDataSource {


    override suspend fun getChartBottom100(): Result<ApiResponse<List<ChartRes>>> {
        return withContext(ioDispatcher){
            safeApiCall {
                imdbScrapingApiService.getChartBottom100()
            }
        }
    }

    override suspend fun getChartBoxOffice(): Result<ApiResponse<ChartBoxOfficeRes>> {
        return withContext(ioDispatcher){
            safeApiCall {
                imdbScrapingApiService.getChartBoxOffice()
            }
        }
    }

    override suspend fun getChartPopular(): Result<ApiResponse<List<ChartRes>>> {
        return withContext(ioDispatcher){
            safeApiCall {
                imdbScrapingApiService.getChartPopular()
            }
        }
    }

    override suspend fun getChartTop250(): Result<ApiResponse<List<ChartRes>>> {
        return withContext(ioDispatcher){
            safeApiCall {
                imdbScrapingApiService.getChartTop250()
            }
        }
    }

    override suspend fun getChartTvPopular(): Result<ApiResponse<List<ChartRes>>> {
        return withContext(ioDispatcher){
            safeApiCall {
                imdbScrapingApiService.getChartTvPopular()
            }
        }
    }

    override suspend fun getChartTopTv250(): Result<ApiResponse<List<ChartRes>>> {
        return withContext(ioDispatcher){
            safeApiCall {
                imdbScrapingApiService.getChartTopTv250()
            }
        }
    }

    override suspend fun getEvents(): Result<ApiResponse<List<EventRes>>> {
        return withContext(ioDispatcher){
            safeApiCall {
                imdbScrapingApiService.getEvents()
            }
        }
    }

    override suspend fun getEventDetails(
        eventId: String,
        year: String
    ): Result<ApiResponse<EventDetailsRes>> {
        return withContext(ioDispatcher){
            safeApiCall {
                imdbScrapingApiService.getEventDetails(eventId, year)
            }
        }
    }

    override suspend fun getGenres(): Result<ApiResponse<List<GenresRes>>> {
        return withContext(ioDispatcher){
            safeApiCall {
                imdbScrapingApiService.getGenres()
            }
        }
    }

    override suspend fun getHome(): Result<ApiResponse<HomeRes>> {

        return withContext(ioDispatcher){
            safeApiCall {
                imdbScrapingApiService.getHome()
            }
        }
    }

    override suspend fun getHomeExtra(): Result<ApiResponse<HomeExtraRes>> {
        return withContext(ioDispatcher){
            safeApiCall {
                imdbScrapingApiService.getHomeExtra()
            }
        }
    }

    override suspend fun getListImages(listId: String): Result<ApiResponse<ImagesRes>> {
        return withContext(ioDispatcher){
            safeApiCall {
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
            safeApiCall {
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
            safeApiCall {
                imdbScrapingApiService.getNameImages(nameId, page)
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
            safeApiCall {
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

    override suspend fun getTitleImages(
        titleId: String,
        page: String
    ): Result<ApiResponse<ImagesRes>> {
        return withContext(ioDispatcher){
            safeApiCall {
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
            safeApiCall {
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
            safeApiCall {
                imdbScrapingApiService.getKeywords()
            }
        }
    }

    override suspend fun getNameDetails(nameId: String): Result<ApiResponse<NameDetailsRes>> {
        return withContext(ioDispatcher){
            safeApiCall {
                imdbScrapingApiService.getNameDetails(nameId)
            }
        }
    }

    override suspend fun getNameAwards(nameId: String): Result<ApiResponse<NameAwardsRes>> {
        return withContext(ioDispatcher){
            safeApiCall {
                imdbScrapingApiService.getNameAwards(nameId)
            }
        }
    }

    override suspend fun getNameBio(nameId: String): Result<ApiResponse<NameBioRes>> {
        return withContext(ioDispatcher){
            safeApiCall {
                imdbScrapingApiService.getNameBio(nameId)
            }
        }
    }

    override suspend fun getNewsDetails(newsId: String): Result<ApiResponse<NewsDetailsRes>> {
        return withContext(ioDispatcher){
            safeApiCall {
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
            safeApiCall {
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
            safeApiCall {
                imdbScrapingApiService.searchTitles(certificates, colors, companies, countries, genres, groups, keywords, languages, locations, plot, releaseDate, role, runtime, sort, start, title, titleType, userRating)
            }
        }
    }

    override suspend fun getTitleDetails(titleId: String): Result<ApiResponse<TitleDetailsRes>> {
        return withContext(ioDispatcher){
            safeApiCall {
                imdbScrapingApiService.getTitleDetails(titleId)
            }
        }
    }

    override suspend fun getTitleAwards(titleId: String): Result<ApiResponse<TitleAwardsRes>> {
        return withContext(ioDispatcher){
            safeApiCall {
                imdbScrapingApiService.getTitleAwards(titleId)
            }
        }
    }

    override suspend fun getTitleFullCredits(titleId: String): Result<ApiResponse<TitleFullCreditsRes>> {
        return withContext(ioDispatcher){
            safeApiCall {
                imdbScrapingApiService.getTitleFullCredits(titleId)
            }
        }
    }

    override suspend fun getTitleParentalGuide(titleId: String): Result<ApiResponse<TitleParentsGuideRes>> {
        return withContext(ioDispatcher){
            safeApiCall {
                imdbScrapingApiService.getTitleParentalGuide(titleId)
            }
        }
    }

    override suspend fun getTitleTechnicalSpecs(titleId: String): Result<ApiResponse<TitleTechnicalSpecsRes>> {
        return withContext(ioDispatcher){
            safeApiCall {
                imdbScrapingApiService.getTitleTechnicalSpecs(titleId)
            }
        }
    }

    override suspend fun getTitlesCalender(): Result<ApiResponse<List<CalenderRes>>> {
        return withContext(ioDispatcher){
            safeApiCall {
                imdbScrapingApiService.getTitlesCalender()
            }
        }
    }

    override suspend fun getTrailersAnticipated(): Result<ApiResponse<List<TrailerRes>>> {
        return withContext(ioDispatcher){
            safeApiCall {
                imdbScrapingApiService.getTrailersAnticipated()
            }
        }
    }

    override suspend fun getTrailersPopular(): Result<ApiResponse<List<TrailerRes>>> {
        return withContext(ioDispatcher){
            safeApiCall {
                imdbScrapingApiService.getTrailersPopular()
            }
        }
    }

    override suspend fun getTrailersRecent(): Result<ApiResponse<List<TrailerRes>>> {
        return withContext(ioDispatcher){
            safeApiCall {
                imdbScrapingApiService.getTrailersRecent()
            }
        }
    }

    override suspend fun getTrailersTrending(): Result<ApiResponse<List<TrailerRes>>> {
        return withContext(ioDispatcher){
            safeApiCall {
                imdbScrapingApiService.getTrailersTrending()
            }
        }
    }

    override suspend fun getVideoDetails(videoId: String): Result<ApiResponse<VideoDetailsRes>> {
        return withContext(ioDispatcher){
            safeApiCall {
                imdbScrapingApiService.getVideoDetails(videoId)
            }
        }
    }

    override suspend fun getNameVideos(
        nameId: String,
        page: String
    ): Result<ApiResponse<VideosRes>> {
        return withContext(ioDispatcher){
            safeApiCall {
                imdbScrapingApiService.getNameVideos(nameId, page)
            }
        }
    }

    override suspend fun getTitleVideos(
        titleId: String,
        page: String
    ): Result<ApiResponse<VideosRes>> {
        return withContext(ioDispatcher){
            safeApiCall {
                imdbScrapingApiService.getTitleVideos(titleId, page)
            }
        }
    }

    override suspend fun suggestAll(
        firstLetter: String,
        term: String
    ): Result<ApiResponse<SuggestRes>> {
        return withContext(ioDispatcher){
            safeApiCall {
                imdbSuggestionApiService.suggestAll(firstLetter,term)
            }
        }
    }

    override suspend fun suggestTitles(
        firstLetter: String,
        term: String
    ): Result<ApiResponse<SuggestRes>> {
        return withContext(ioDispatcher){
            safeApiCall {
                imdbSuggestionApiService.suggestTitles(firstLetter,term)
            }
        }    }

    override suspend fun suggestNames(
        firstLetter: String,
        term: String
    ): Result<ApiResponse<SuggestRes>> {
        return withContext(ioDispatcher){
            safeApiCall {
                imdbSuggestionApiService.suggestNames(firstLetter,term)
            }
        }
    }
}