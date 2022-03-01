package com.mabahmani.data.vo.res

import com.mabahmani.domain.vo.enum.HomeMediaType
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class HomeResTest {
    private lateinit var model: HomeRes

    @Before
    fun setup() {
        model = HomeRes(
            HomeRes.BoxOffice(
                "2021-08-12",
                "2021-08-19",
                listOf(
                    HomeRes.BoxOffice.Data(
                        568987799,
                        "US",
                        5,
                        "title",
                        "tt5689999"
                    )
                )
            ),
            listOf(
                HomeRes.EditorPick(
                    "caption",
                    "cover",
                    "id",
                    false,
                    "",
                    "rm565556",
                    "title",
                    null
                )
            ),
            listOf(
                HomeRes.FeaturedToday(
                    "caption",
                    "cover",
                    "id",
                    true,
                    "",
                    null,
                    "title",
                    null
                )
            ),
            listOf(
                HomeRes.ImdbOriginal(
                    "caption",
                    "cover",
                    "id",
                    false,
                    "",
                    "rm565556",
                    "title",
                    true
                )
            ),
            listOf(
                HomeRes.News(
                    "ni569897",
                    "2021-05-16",
                    "image",
                    "source",
                    "title"
                )
            ),
            listOf(
                HomeRes.Trailer(
                    "title",
                    "subtitle",
                    "cover",
                    "tt5684686",
                    "vi54889",
                    "preview",
                    "4:30"
                )
            )
        )
    }

    @Test
    fun `test HomeRes model to Home domain model  conversion`() {
        val result = model.toHome()

        assertEquals("source", result.news[0].source)
        assertEquals("May 16", result.news[0].date)
        assertEquals("image", result.news[0].image.value)
        assertEquals("ni569897", result.news[0].newsId.value)
        assertEquals("title", result.news[0].title)

        assertEquals("subtitle", result.trailers[0].caption)
        assertEquals("4:30", result.trailers[0].duration)
        assertEquals("title", result.trailers[0].title)
        assertEquals("cover", result.trailers[0].titleCover.value)
        assertEquals("tt5684686", result.trailers[0].titleId.value)
        assertEquals("vi54889", result.trailers[0].videoId.value)
        assertEquals("preview", result.trailers[0].videoPreview.value)

        assertEquals("2021-08-12", result.boxOffice.startDate)
        assertEquals("2021-08-19", result.boxOffice.endDate)
        assertEquals("title", result.boxOffice.boxOfficeItems?.get(0)?.title)
        assertEquals("tt5689999", result.boxOffice.boxOfficeItems?.get(0)?.titleId?.value)
        assertEquals(null, result.boxOffice.boxOfficeItems?.get(0)?.image)
        assertEquals("", result.boxOffice.boxOfficeItems?.get(0)?.gross)
        assertEquals("568987799", result.boxOffice.boxOfficeItems?.get(0)?.weekendGross)
        assertEquals("", result.boxOffice.boxOfficeItems?.get(0)?.weeks)

        assertEquals(HomeMediaType.GALLERY, result.editorPicks[0].type)
        assertEquals("caption", result.editorPicks[0].caption)
        assertEquals(null, result.editorPicks[0].date)
        assertEquals("id", result.editorPicks[0].id)
        assertEquals("cover", result.editorPicks[0].image.value)
        assertEquals("title", result.editorPicks[0].title)

        assertEquals(HomeMediaType.LIST, result.featuredToday[0].type)
        assertEquals("caption", result.featuredToday[0].caption)
        assertEquals(null, result.featuredToday[0].date)
        assertEquals("id", result.featuredToday[0].id)
        assertEquals("cover", result.featuredToday[0].image.value)
        assertEquals("title", result.featuredToday[0].title)


        assertEquals(HomeMediaType.VIDEO, result.imdbOriginals[0].type)
        assertEquals("caption", result.imdbOriginals[0].caption)
        assertEquals(null, result.imdbOriginals[0].date)
        assertEquals("id", result.imdbOriginals[0].id)
        assertEquals("cover", result.imdbOriginals[0].image.value)
        assertEquals("title", result.imdbOriginals[0].title)



    }

}