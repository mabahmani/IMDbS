package com.mabahmani.data.ds

import com.mabahmani.data.remote.ImdbScrapingApiService
import com.mabahmani.data.remote.ImdbSuggestionApiService
import com.mabahmani.data.vo.generic.ApiResponse
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import okhttp3.OkHttpClient
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class RemoteDataSourceImplTest {

    @MockK
    lateinit var imdbScrapingApiService: ImdbScrapingApiService

    @MockK
    lateinit var imdbSuggestionApiService: ImdbSuggestionApiService

    private lateinit var remoteDataSource: RemoteDataSource

    private lateinit var coroutineDispatcher: TestDispatcher

    private lateinit var okHttpClient: OkHttpClient

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        coroutineDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(coroutineDispatcher)
        okHttpClient = OkHttpClient()
        remoteDataSource = RemoteDataSourceImpl(
            coroutineDispatcher,
            okHttpClient,
            imdbScrapingApiService,
            imdbSuggestionApiService
        )
    }

    @Test
    fun `test getChartBottom100 success response`() = runTest {

        coEvery { imdbScrapingApiService.getHome() } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.getHome()

        coVerify {
            imdbScrapingApiService.getHome()
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test getChartBottom100 failure response`() = runTest {

        coEvery { imdbScrapingApiService.getHome() } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.getHome()

        coVerify {
            imdbScrapingApiService.getHome()
        }

        assert(result.isFailure)
    }

    @Test
    fun `test getChartBoxOffice success response`() = runTest {

        coEvery { imdbScrapingApiService.getChartBoxOffice() } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.getChartBoxOffice()

        coVerify {
            imdbScrapingApiService.getChartBoxOffice()
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test getChartBoxOffice failure response`() = runTest {

        coEvery { imdbScrapingApiService.getChartBoxOffice() } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.getChartBoxOffice()

        coVerify {
            imdbScrapingApiService.getChartBoxOffice()
        }

        assert(result.isFailure)
    }

    @Test
    fun `test getChartPopular success response`() = runTest {

        coEvery { imdbScrapingApiService.getChartPopular() } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.getChartPopular()

        coVerify {
            imdbScrapingApiService.getChartPopular()
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test getChartPopular failure response`() = runTest {

        coEvery { imdbScrapingApiService.getChartPopular() } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.getChartPopular()

        coVerify {
            imdbScrapingApiService.getChartPopular()
        }

        assert(result.isFailure)
    }

    @Test
    fun `test getChartTop250 success response`() = runTest {

        coEvery { imdbScrapingApiService.getChartTop250() } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.getChartTop250()

        coVerify {
            imdbScrapingApiService.getChartTop250()
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test getChartTop250 failure response`() = runTest {

        coEvery { imdbScrapingApiService.getChartTop250() } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.getChartTop250()

        coVerify {
            imdbScrapingApiService.getChartTop250()
        }

        assert(result.isFailure)
    }

    @Test
    fun `test getChartTvPopular success response`() = runTest {

        coEvery { imdbScrapingApiService.getChartTvPopular() } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.getChartTvPopular()

        coVerify {
            imdbScrapingApiService.getChartTvPopular()
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test getChartTvPopular failure response`() = runTest {

        coEvery { imdbScrapingApiService.getChartTvPopular() } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.getChartTvPopular()

        coVerify {
            imdbScrapingApiService.getChartTvPopular()
        }

        assert(result.isFailure)
    }

    @Test
    fun `test getChartTopTv250 success response`() = runTest {

        coEvery { imdbScrapingApiService.getChartTopTv250() } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.getChartTopTv250()

        coVerify {
            imdbScrapingApiService.getChartTopTv250()
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test getChartTopTv250 failure response`() = runTest {

        coEvery { imdbScrapingApiService.getChartTopTv250() } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.getChartTopTv250()

        coVerify {
            imdbScrapingApiService.getChartTopTv250()
        }

        assert(result.isFailure)
    }

    @Test
    fun `test getEvents success response`() = runTest {

        coEvery { imdbScrapingApiService.getEvents() } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.getEvents()

        coVerify {
            imdbScrapingApiService.getEvents()
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test getEvents failure response`() = runTest {

        coEvery { imdbScrapingApiService.getEvents() } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.getEvents()

        coVerify {
            imdbScrapingApiService.getEvents()
        }

        assert(result.isFailure)
    }

    @Test
    fun `test getEventDetails success response`() = runTest {

        coEvery { imdbScrapingApiService.getEventDetails(any(),any()) } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.getEventDetails("ev00000","2000")

        coVerify {
            imdbScrapingApiService.getEventDetails("ev00000","2000")
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test getEventDetails failure response`() = runTest {

        coEvery { imdbScrapingApiService.getEventDetails(any(),any()) } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.getEventDetails("ev00000","2000")

        coVerify {
            imdbScrapingApiService.getEventDetails("ev00000","2000")
        }

        assert(result.isFailure)
    }

    @Test
    fun `test getGenres success response`() = runTest {

        coEvery { imdbScrapingApiService.getGenres() } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.getGenres()

        coVerify {
            imdbScrapingApiService.getGenres()
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test getGenres failure response`() = runTest {

        coEvery { imdbScrapingApiService.getGenres() } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.getGenres()

        coVerify {
            imdbScrapingApiService.getGenres()
        }

        assert(result.isFailure)
    }

    @Test
    fun `test getHome success response`() = runTest {

        coEvery { imdbScrapingApiService.getHome() } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.getHome()

        coVerify {
            imdbScrapingApiService.getHome()
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test getHome failure response`() = runTest {

        coEvery { imdbScrapingApiService.getHome() } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.getHome()

        coVerify {
            imdbScrapingApiService.getHome()
        }

        assert(result.isFailure)
    }

    @Test
    fun `test getHomeExtra success response`() = runTest {

        coEvery { imdbScrapingApiService.getHomeExtra() } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.getHomeExtra()

        coVerify {
            imdbScrapingApiService.getHomeExtra()
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test getHomeExtra failure response`() = runTest {

        coEvery { imdbScrapingApiService.getHomeExtra() } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.getHomeExtra()

        coVerify {
            imdbScrapingApiService.getHomeExtra()
        }

        assert(result.isFailure)
    }

    @Test
    fun `test getListImages success response`() = runTest {

        coEvery { imdbScrapingApiService.getListImages(any()) } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.getListImages("li000000")

        coVerify {
            imdbScrapingApiService.getListImages("li000000")
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test getListImages failure response`() = runTest {

        coEvery { imdbScrapingApiService.getListImages(any()) } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.getListImages("li000000")

        coVerify {
            imdbScrapingApiService.getListImages("li000000")
        }

        assert(result.isFailure)
    }

    @Test
    fun `test getListImagesWithDetails success response`() = runTest {

        coEvery { imdbScrapingApiService.getListImagesWithDetails(any(), any(),any(), any(),any(), any()) } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.getListImagesWithDetails("li000000","5","3",0,6,"rm000000")

        coVerify {
            imdbScrapingApiService.getListImagesWithDetails("li000000","5","3",0,6,"rm000000")
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test getListImagesWithDetails failure response`() = runTest {

        coEvery { imdbScrapingApiService.getListImagesWithDetails(any(), any(),any(), any(),any(), any()) } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.getListImagesWithDetails("li000000","5","3",0,6,"rm000000")

        coVerify {
            imdbScrapingApiService.getListImagesWithDetails("li000000","5","3",0,6,"rm000000")
        }

        assert(result.isFailure)
    }

    @Test
    fun `test getNameImages success response`() = runTest {

        coEvery { imdbScrapingApiService.getNameImages(any(), any()) } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.getNameImages("nm000000","5")

        coVerify {
            imdbScrapingApiService.getNameImages("nm000000","5")
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test getNameImages failure response`() = runTest {

        coEvery { imdbScrapingApiService.getNameImages(any(), any()) } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.getNameImages("nm000000","5")

        coVerify {
            imdbScrapingApiService.getNameImages("nm000000","5")
        }

        assert(result.isFailure)
    }

    @Test
    fun `test getNameImagesWithDetails success response`() = runTest {

        coEvery { imdbScrapingApiService.getNameImagesWithDetails(any(), any(),any(), any(),any(), any()) } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.getNameImagesWithDetails("nm000000","5","3",0,6,"rm000000")

        coVerify {
            imdbScrapingApiService.getNameImagesWithDetails("nm000000","5","3",0,6,"rm000000")
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test getNameImagesWithDetails failure response`() = runTest {

        coEvery { imdbScrapingApiService.getNameImagesWithDetails(any(), any(),any(), any(),any(), any()) } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.getNameImagesWithDetails("nm000000","5","3",0,6,"rm000000")

        coVerify {
            imdbScrapingApiService.getNameImagesWithDetails("nm000000","5","3",0,6,"rm000000")
        }

        assert(result.isFailure)
    }

    @Test
    fun `test getTitleImages success response`() = runTest {

        coEvery { imdbScrapingApiService.getTitleImages(any(), any()) } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.getTitleImages("tt000000","1")

        coVerify {
            imdbScrapingApiService.getTitleImages("tt000000","1")
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test getTitleImages failure response`() = runTest {

        coEvery { imdbScrapingApiService.getTitleImages(any(), any()) } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.getTitleImages("tt000000","1")

        coVerify {
            imdbScrapingApiService.getTitleImages("tt000000","1")
        }

        assert(result.isFailure)
    }

    @Test
    fun `test getTitleImagesWithDetails success response`() = runTest {

        coEvery { imdbScrapingApiService.getTitleImagesWithDetails(any(), any(),any(), any(),any(), any()) } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.getTitleImagesWithDetails("tt000000","5","3",0,6,"rm000000")

        coVerify {
            imdbScrapingApiService.getTitleImagesWithDetails("tt000000","5","3",0,6,"rm000000")
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test getTitleImagesWithDetails failure response`() = runTest {

        coEvery { imdbScrapingApiService.getTitleImagesWithDetails(any(), any(),any(), any(),any(), any()) } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.getTitleImagesWithDetails("tt000000","5","3",0,6,"rm000000")

        coVerify {
            imdbScrapingApiService.getTitleImagesWithDetails("tt000000","5","3",0,6,"rm000000")
        }

        assert(result.isFailure)
    }

    @Test
    fun `test getKeywords success response`() = runTest {

        coEvery { imdbScrapingApiService.getKeywords() } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.getKeywords()

        coVerify {
            imdbScrapingApiService.getKeywords()
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test getKeywords failure response`() = runTest {

        coEvery { imdbScrapingApiService.getKeywords() } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.getKeywords()

        coVerify {
            imdbScrapingApiService.getKeywords()
        }

        assert(result.isFailure)
    }

    @Test
    fun `test getNameDetails success response`() = runTest {

        coEvery { imdbScrapingApiService.getNameDetails(any()) } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.getNameDetails("nm000000")

        coVerify {
            imdbScrapingApiService.getNameDetails("nm000000")
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test getNameDetails failure response`() = runTest {

        coEvery { imdbScrapingApiService.getNameDetails(any()) } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.getNameDetails("nm000000")

        coVerify {
            imdbScrapingApiService.getNameDetails("nm000000")
        }

        assert(result.isFailure)
    }

    @Test
    fun `test getNameAwards success response`() = runTest {

        coEvery { imdbScrapingApiService.getNameAwards(any()) } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.getNameAwards("nm000000")

        coVerify {
            imdbScrapingApiService.getNameAwards("nm000000")
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test getNameAwards failure response`() = runTest {

        coEvery { imdbScrapingApiService.getNameAwards(any()) } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.getNameAwards("nm000000")

        coVerify {
            imdbScrapingApiService.getNameAwards("nm000000")
        }

        assert(result.isFailure)
    }

    @Test
    fun `test getNameBio success response`() = runTest {

        coEvery { imdbScrapingApiService.getNameBio(any()) } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.getNameBio("nm000000")

        coVerify {
            imdbScrapingApiService.getNameBio("nm000000")
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test getNameBio failure response`() = runTest {

        coEvery { imdbScrapingApiService.getNameBio(any()) } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.getNameBio("nm000000")

        coVerify {
            imdbScrapingApiService.getNameBio("nm000000")
        }

        assert(result.isFailure)
    }

    @Test
    fun `test getNewsDetails success response`() = runTest {

        coEvery { imdbScrapingApiService.getNewsDetails(any()) } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.getNewsDetails("nm000000")

        coVerify {
            imdbScrapingApiService.getNewsDetails("nm000000")
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test getNewsDetails failure response`() = runTest {

        coEvery { imdbScrapingApiService.getNewsDetails(any()) } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.getNewsDetails("nm000000")

        coVerify {
            imdbScrapingApiService.getNewsDetails("nm000000")
        }

        assert(result.isFailure)
    }

    @Test
    fun `test searchNames success response`() = runTest {

        coEvery { imdbScrapingApiService.searchNames(any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any()) } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.searchNames("bio","1999","08-05","iran","2050","iran","male","groups","farhadi","tt5566",null,"starSing","1")

        coVerify {
            imdbScrapingApiService.searchNames("bio","1999","08-05","iran","2050","iran","male","groups","farhadi","tt5566",null,"starSing","1")
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test searchNames failure response`() = runTest {

        coEvery { imdbScrapingApiService.searchNames(any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any()) } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.searchNames("bio","1999","08-05","iran","2050","iran","male","groups","farhadi","tt5566",null,"starSing","1")

        coVerify {
            imdbScrapingApiService.searchNames("bio","1999","08-05","iran","2050","iran","male","groups","farhadi","tt5566",null,"starSing","1")

        }

        assert(result.isFailure)
    }

    @Test
    fun `test searchTitles success response`() = runTest {

        coEvery { imdbScrapingApiService.searchTitles(any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any()) } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.searchTitles("bio","1999","08-05","iran","2050","iran","male","groups","farhadi","tt5566",null,"starSing","1",null,"5","eli", "oscar","5.6-8.9")

        coVerify {
            imdbScrapingApiService.searchTitles("bio","1999","08-05","iran","2050","iran","male","groups","farhadi","tt5566",null,"starSing","1",null,"5","eli", "oscar","5.6-8.9")        }

        assert(result.isSuccess)
    }

    @Test
    fun `test searchTitles failure response`() = runTest {

        coEvery { imdbScrapingApiService.searchTitles(any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any()) } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.searchTitles("bio","1999","08-05","iran","2050","iran","male","groups","farhadi","tt5566",null,"starSing","1",null,"5","eli", "oscar","5.6-8.9")
        coVerify {
            imdbScrapingApiService.searchTitles("bio","1999","08-05","iran","2050","iran","male","groups","farhadi","tt5566",null,"starSing","1",null,"5","eli", "oscar","5.6-8.9")
        }

        assert(result.isFailure)
    }

    @Test
    fun `test getTitleDetails success response`() = runTest {

        coEvery { imdbScrapingApiService.getTitleDetails(any()) } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.getTitleDetails("tt000000")

        coVerify {
            imdbScrapingApiService.getTitleDetails("tt000000")
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test getTitleDetails failure response`() = runTest {

        coEvery { imdbScrapingApiService.getTitleDetails(any()) } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.getTitleDetails("tt000000")

        coVerify {
            imdbScrapingApiService.getTitleDetails("tt000000")
        }

        assert(result.isFailure)
    }

    @Test
    fun `test getTitleAwards success response`() = runTest {

        coEvery { imdbScrapingApiService.getTitleAwards(any()) } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.getTitleAwards("tt000000")

        coVerify {
            imdbScrapingApiService.getTitleAwards("tt000000")
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test getTitleAwards failure response`() = runTest {

        coEvery { imdbScrapingApiService.getTitleAwards(any()) } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.getTitleAwards("tt000000")

        coVerify {
            imdbScrapingApiService.getTitleAwards("tt000000")
        }

        assert(result.isFailure)
    }

    @Test
    fun `test getTitleFullCredits success response`() = runTest {

        coEvery { imdbScrapingApiService.getTitleFullCredits(any()) } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.getTitleFullCredits("tt000000")

        coVerify {
            imdbScrapingApiService.getTitleFullCredits("tt000000")
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test getTitleFullCredits failure response`() = runTest {

        coEvery { imdbScrapingApiService.getTitleFullCredits(any()) } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.getTitleFullCredits("tt000000")

        coVerify {
            imdbScrapingApiService.getTitleFullCredits("tt000000")
        }

        assert(result.isFailure)
    }

    @Test
    fun `test getTitleParentalGuide success response`() = runTest {

        coEvery { imdbScrapingApiService.getTitleParentalGuide(any()) } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.getTitleParentalGuide("tt000000")

        coVerify {
            imdbScrapingApiService.getTitleParentalGuide("tt000000")
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test getTitleParentalGuide failure response`() = runTest {

        coEvery { imdbScrapingApiService.getTitleParentalGuide(any()) } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.getTitleParentalGuide("tt000000")

        coVerify {
            imdbScrapingApiService.getTitleParentalGuide("tt000000")
        }

        assert(result.isFailure)
    }

    @Test
    fun `test getTitleTechnicalSpecs success response`() = runTest {

        coEvery { imdbScrapingApiService.getTitleTechnicalSpecs(any()) } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.getTitleTechnicalSpecs("tt000000")

        coVerify {
            imdbScrapingApiService.getTitleTechnicalSpecs("tt000000")
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test getTitleTechnicalSpecs failure response`() = runTest {

        coEvery { imdbScrapingApiService.getTitleTechnicalSpecs(any()) } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.getTitleTechnicalSpecs("tt000000")

        coVerify {
            imdbScrapingApiService.getTitleTechnicalSpecs("tt000000")
        }

        assert(result.isFailure)
    }

    @Test
    fun `test getTitlesCalender success response`() = runTest {

        coEvery { imdbScrapingApiService.getTitlesCalender() } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.getTitlesCalender()

        coVerify {
            imdbScrapingApiService.getTitlesCalender()
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test getTitlesCalender failure response`() = runTest {

        coEvery { imdbScrapingApiService.getTitlesCalender() } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.getTitlesCalender()

        coVerify {
            imdbScrapingApiService.getTitlesCalender()
        }

        assert(result.isFailure)
    }


    @Test
    fun `test getTrailersAnticipated success response`() = runTest {

        coEvery { imdbScrapingApiService.getTrailersAnticipated() } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.getTrailersAnticipated()

        coVerify {
            imdbScrapingApiService.getTrailersAnticipated()
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test getTrailersAnticipated failure response`() = runTest {

        coEvery { imdbScrapingApiService.getTrailersAnticipated() } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.getTrailersAnticipated()

        coVerify {
            imdbScrapingApiService.getTrailersAnticipated()
        }

        assert(result.isFailure)
    }

    @Test
    fun `test getTrailersPopular success response`() = runTest {

        coEvery { imdbScrapingApiService.getTrailersPopular() } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.getTrailersPopular()

        coVerify {
            imdbScrapingApiService.getTrailersPopular()
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test getTrailersPopular failure response`() = runTest {

        coEvery { imdbScrapingApiService.getTrailersPopular() } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.getTrailersPopular()

        coVerify {
            imdbScrapingApiService.getTrailersPopular()
        }

        assert(result.isFailure)
    }


    @Test
    fun `test getTrailersRecent success response`() = runTest {

        coEvery { imdbScrapingApiService.getTrailersRecent() } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.getTrailersRecent()

        coVerify {
            imdbScrapingApiService.getTrailersRecent()
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test getTrailersRecent failure response`() = runTest {

        coEvery { imdbScrapingApiService.getTrailersRecent() } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.getTrailersRecent()

        coVerify {
            imdbScrapingApiService.getTrailersRecent()
        }

        assert(result.isFailure)
    }

    @Test
    fun `test getTrailersTrending success response`() = runTest {

        coEvery { imdbScrapingApiService.getTrailersTrending() } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.getTrailersTrending()

        coVerify {
            imdbScrapingApiService.getTrailersTrending()
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test getTrailersTrending failure response`() = runTest {

        coEvery { imdbScrapingApiService.getTrailersTrending() } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.getTrailersTrending()

        coVerify {
            imdbScrapingApiService.getTrailersTrending()
        }

        assert(result.isFailure)
    }

    @Test
    fun `test getVideoDetails success response`() = runTest {

        coEvery { imdbScrapingApiService.getVideoDetails(any()) } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.getVideoDetails("vi000000")

        coVerify {
            imdbScrapingApiService.getVideoDetails("vi000000")
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test getVideoDetails failure response`() = runTest {

        coEvery { imdbScrapingApiService.getVideoDetails(any()) } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.getVideoDetails("vi000000")

        coVerify {
            imdbScrapingApiService.getVideoDetails("vi000000")
        }

        assert(result.isFailure)
    }


    @Test
    fun `test getNameVideos success response`() = runTest {

        coEvery { imdbScrapingApiService.getNameVideos(any(), any()) } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.getNameVideos("nm000000","1")

        coVerify {
            imdbScrapingApiService.getNameVideos("nm000000", "1")
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test getNameVideos failure response`() = runTest {

        coEvery { imdbScrapingApiService.getNameVideos(any(), any()) } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.getNameVideos("nm000000","1")

        coVerify {
            imdbScrapingApiService.getNameVideos("nm000000","1")
        }

        assert(result.isFailure)
    }

    @Test
    fun `test getTitleVideos success response`() = runTest {

        coEvery { imdbScrapingApiService.getTitleVideos(any(), any()) } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.getTitleVideos("tt000000","2")

        coVerify {
            imdbScrapingApiService.getTitleVideos("tt000000", "2")
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test getTitleVideos failure response`() = runTest {

        coEvery { imdbScrapingApiService.getTitleVideos(any(), any()) } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.getTitleVideos("tt000000","2")

        coVerify {
            imdbScrapingApiService.getTitleVideos("tt000000","2")
        }

        assert(result.isFailure)
    }

    @Test
    fun `test suggestAll success response`() = runTest {

        coEvery { imdbSuggestionApiService.suggestAll(any(), any()) } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.suggestAll("h","harry")

        coVerify {
            imdbSuggestionApiService.suggestAll("h", "harry")
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test suggestAll failure response`() = runTest {

        coEvery { imdbSuggestionApiService.suggestAll(any(), any()) } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.suggestAll("h","harry")

        coVerify {
            imdbSuggestionApiService.suggestAll("h","harry")
        }

        assert(result.isFailure)
    }

    @Test
    fun `test suggestTitles success response`() = runTest {

        coEvery { imdbSuggestionApiService.suggestTitles(any(), any()) } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.suggestTitles("h","harry")

        coVerify {
            imdbSuggestionApiService.suggestTitles("h", "harry")
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test suggestTitles failure response`() = runTest {

        coEvery { imdbSuggestionApiService.suggestTitles(any(), any()) } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.suggestTitles("h","harry")

        coVerify {
            imdbSuggestionApiService.suggestTitles("h","harry")
        }

        assert(result.isFailure)
    }

    @Test
    fun `test suggestNames success response`() = runTest {

        coEvery { imdbSuggestionApiService.suggestNames(any(), any()) } returns
                Response.success(
                    ApiResponse(
                        mockk(), true, ""
                    )
                )

        val result = remoteDataSource.suggestNames("h","harry")

        coVerify {
            imdbSuggestionApiService.suggestNames("h", "harry")
        }

        assert(result.isSuccess)
    }

    @Test
    fun `test suggestNames failure response`() = runTest {

        coEvery { imdbSuggestionApiService.suggestNames(any(), any()) } returns
                Response.error(
                    400,
                    "error".toResponseBody()
                )

        val result = remoteDataSource.suggestNames("h","harry")

        coVerify {
            imdbSuggestionApiService.suggestNames("h","harry")
        }

        assert(result.isFailure)
    }

    @After
    fun finalize() {
        unmockkAll()
        Dispatchers.resetMain()
    }
}