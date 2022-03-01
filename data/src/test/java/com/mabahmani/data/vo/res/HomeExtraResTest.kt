package com.mabahmani.data.vo.res

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class HomeExtraResTest {
    private lateinit var model: HomeExtraRes

    @Before
    fun setup() {
        model = HomeExtraRes(
            listOf(
                HomeExtraRes.BornToday(
                    "2000-11-05",
                    "2022-10-15",
                    "image url",
                    "nm165899",
                    "name"
                )
            ),
            listOf(
                HomeExtraRes.ComingSoonMovie(
                    "certificates",
                    "cover",
                    8.9f,
                    "02",
                    "10",
                    "2005",
                    160,
                    "title",
                    "tt568999",
                    "vi5688",
                    "video name",
                    "video preview",
                    20,
                    569889
                )
            ),
            listOf(
                HomeExtraRes.FanPicksTitle(
                    "certificates",
                    "cover",
                    8.9f,
                    "02",
                    "10",
                    "2005",
                    160,
                    "title",
                    "tt568999",
                    "vi5688",
                    569889
                )
            ),
            listOf(
                HomeExtraRes.ShowTimesTitle(
                    "certificates",
                    "cover",
                    8.9f,
                    "02",
                    "10",
                    "2005",
                    160,
                    "title",
                    "tt568999",
                    "vi5688",
                    569889
                )
            ),
            listOf(
                HomeExtraRes.StreamingTitle(
                    "stream title",
                    listOf(
                        HomeExtraRes.StreamingTitle.Title(
                            "certificates",
                            "cover",
                            8.9f,
                            "02",
                            "10",
                            "2005",
                            160,
                            "title",
                            "tt568999",
                            "vi5688",
                            569889
                        )
                    )
                )
            )

        )
    }

    @Test
    fun `test HomeExtraRes model to HomeExtra domain model  conversion`() {
        val result = model.toHomeExtra()

        assertEquals("2000", result.bornTodayList[0].birth)
        assertEquals("2022", result.bornTodayList[0].death)
        assertEquals("22", result.bornTodayList[0].age)
        assertEquals("image url", result.bornTodayList[0].avatar.value)
        assertEquals("name", result.bornTodayList[0].name)
        assertEquals("nm165899", result.bornTodayList[0].nameId.value)

        assertEquals("0:20", result.comingSoonTitles[0].videoRuntime)
        assertEquals("video preview", result.comingSoonTitles[0].videoPreview?.value)
        assertEquals("video name", result.comingSoonTitles[0].videoName)
        assertEquals("vi5688", result.comingSoonTitles[0].videoId?.value)
        assertEquals("569889", result.comingSoonTitles[0].voteCount)
        assertEquals("tt568999", result.comingSoonTitles[0].titleId?.value)
        assertEquals("title", result.comingSoonTitles[0].title)
        assertEquals(null, result.comingSoonTitles[0].summary)
        assertEquals(null, result.comingSoonTitles[0].stars)
        assertEquals(null, result.comingSoonTitles[0].directors)
        assertEquals("2:40", result.comingSoonTitles[0].runtime)
        assertEquals("8.9", result.comingSoonTitles[0].rate)
        assertEquals(null, result.comingSoonTitles[0].gross)
        assertEquals(null, result.comingSoonTitles[0].genres)
        assertEquals("cover", result.comingSoonTitles[0].cover?.value)
        assertEquals("certificates", result.comingSoonTitles[0].certificate)
        assertEquals("2005", result.comingSoonTitles[0].releaseYear)
        assertEquals("10", result.comingSoonTitles[0].releaseMonth)
        assertEquals("02", result.comingSoonTitles[0].releaseDay)


        assertEquals(null, result.fanPicksTitles[0].videoRuntime)
        assertEquals(null, result.fanPicksTitles[0].videoPreview?.value)
        assertEquals(null, result.fanPicksTitles[0].videoName)
        assertEquals("vi5688", result.fanPicksTitles[0].videoId?.value)
        assertEquals("569889", result.fanPicksTitles[0].voteCount)
        assertEquals("tt568999", result.fanPicksTitles[0].titleId?.value)
        assertEquals("title", result.fanPicksTitles[0].title)
        assertEquals(null, result.fanPicksTitles[0].summary)
        assertEquals(null, result.fanPicksTitles[0].stars)
        assertEquals(null, result.fanPicksTitles[0].directors)
        assertEquals("2:40", result.fanPicksTitles[0].runtime)
        assertEquals("8.9", result.fanPicksTitles[0].rate)
        assertEquals(null, result.fanPicksTitles[0].gross)
        assertEquals(null, result.fanPicksTitles[0].genres)
        assertEquals("cover", result.fanPicksTitles[0].cover?.value)
        assertEquals("certificates", result.fanPicksTitles[0].certificate)
        assertEquals("2005", result.fanPicksTitles[0].releaseYear)
        assertEquals("10", result.fanPicksTitles[0].releaseMonth)
        assertEquals("02", result.fanPicksTitles[0].releaseDay)


        assertEquals(null, result.showTimesTitles[0].videoRuntime)
        assertEquals(null, result.showTimesTitles[0].videoPreview?.value)
        assertEquals(null, result.showTimesTitles[0].videoName)
        assertEquals("vi5688", result.showTimesTitles[0].videoId?.value)
        assertEquals("569889", result.showTimesTitles[0].voteCount)
        assertEquals("tt568999", result.showTimesTitles[0].titleId?.value)
        assertEquals("title", result.showTimesTitles[0].title)
        assertEquals(null, result.showTimesTitles[0].summary)
        assertEquals(null, result.showTimesTitles[0].stars)
        assertEquals(null, result.showTimesTitles[0].directors)
        assertEquals("2:40", result.showTimesTitles[0].runtime)
        assertEquals("8.9", result.showTimesTitles[0].rate)
        assertEquals(null, result.showTimesTitles[0].gross)
        assertEquals(null, result.showTimesTitles[0].genres)
        assertEquals("cover", result.showTimesTitles[0].cover?.value)
        assertEquals("certificates", result.showTimesTitles[0].certificate)
        assertEquals("2005", result.showTimesTitles[0].releaseYear)
        assertEquals("10", result.showTimesTitles[0].releaseMonth)
        assertEquals("02", result.showTimesTitles[0].releaseDay)

        assertEquals("stream title", result.streamingTitles[0].name)
        assertEquals(null, result.streamingTitles[0].movies[0].videoRuntime)
        assertEquals(null, result.streamingTitles[0].movies[0].videoPreview?.value)
        assertEquals(null, result.streamingTitles[0].movies[0].videoName)
        assertEquals("vi5688", result.streamingTitles[0].movies[0].videoId?.value)
        assertEquals("569889", result.streamingTitles[0].movies[0].voteCount)
        assertEquals("tt568999", result.streamingTitles[0].movies[0].titleId?.value)
        assertEquals("title", result.streamingTitles[0].movies[0].title)
        assertEquals(null, result.streamingTitles[0].movies[0].summary)
        assertEquals(null, result.streamingTitles[0].movies[0].stars)
        assertEquals(null, result.streamingTitles[0].movies[0].directors)
        assertEquals("2:40", result.streamingTitles[0].movies[0].runtime)
        assertEquals("8.9", result.streamingTitles[0].movies[0].rate)
        assertEquals(null, result.streamingTitles[0].movies[0].gross)
        assertEquals(null, result.streamingTitles[0].movies[0].genres)
        assertEquals("cover", result.streamingTitles[0].movies[0].cover?.value)
        assertEquals("certificates", result.streamingTitles[0].movies[0].certificate)
        assertEquals("2005", result.streamingTitles[0].movies[0].releaseYear)
        assertEquals("10", result.streamingTitles[0].movies[0].releaseMonth)
        assertEquals("02", result.streamingTitles[0].movies[0].releaseDay)


    }

}