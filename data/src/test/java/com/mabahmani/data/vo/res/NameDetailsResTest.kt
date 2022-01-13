package com.mabahmani.data.vo.res

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NameDetailsResTest {
    private lateinit var model: NameDetailsRes

    @Before
    fun setup() {
        model = NameDetailsRes(
            "avatar",
            "bioSummary",
            "1980-05-10",
            "5-10",
            "1980",
            "Canada",
            listOf(
                NameDetailsRes.Filmography(
                    listOf(
                        NameDetailsRes.Filmography.Credit(
                        listOf(NameDetailsRes.Filmography.Credit.Episode("epiTitle", "tt598888","epiSubtitle")),
                            "filmTitle",
                            "tt898998",
                            "filmSubtitle",
                            "1995"
                    )),
                    "headTitle",
                    "56"
                )
            ),
            "nm5555688",
            listOf("jobs"),
            listOf(
                NameDetailsRes.KnownFor(
                    "knCover",
                    "knInMovieName",
                    "knTitle",
                    "tt5879411",
                    "1892"
                )
            ),
            "name",
            listOf(
                NameDetailsRes.PersonalDetail(
                    listOf(NameDetailsRes.PersonalDetail.LinkText("linkText", "linkTextUrl")),
                    "pdSubtitle",
                    "pdTitle"
                )
            ),
            listOf(
                NameDetailsRes.Photo(
                    "photoID",
                    "photoUrl"
                )
            ),
            listOf(
                NameDetailsRes.RelatedVideo(
                    "rvCover",
                    "rvTitle",
                    "vi5999987"
                )
            ),
            NameDetailsRes.Trailer(
                "vi4876655",
                "tCover",
                "tCaption"
            )
        )
    }

    @Test
    fun `test NameDetailsRes model to NameDetails domain model  conversion`() {
        val result = model.toNameDetails()

        assertEquals("avatar", result.avatar.value)
        assertEquals("name", result.name)
        assertEquals("nm5555688", result.nameId.value)
        assertEquals("bioSummary", result.bioSummary)
        assertEquals("1980-05-10", result.birthDate)
        assertEquals("5-10", result.birthDateMonthDay)
        assertEquals("Canada", result.birthPlace)
        assertEquals("headTitle", result.filmographies[0].title)
        assertEquals("filmTitle", result.filmographies[0].credits[0].title)
        assertEquals("tt898998", result.filmographies[0].credits[0].titleId.value)
        assertEquals("filmSubtitle", result.filmographies[0].credits[0].subtitle)
        assertEquals("1995", result.filmographies[0].credits[0].year)
        assertEquals("epiTitle", result.filmographies[0].credits[0].episodes[0].title)
        assertEquals("epiSubtitle", result.filmographies[0].credits[0].episodes[0].subtitle)
        assertEquals("tt598888", result.filmographies[0].credits[0].episodes[0].titleId.value)
        assertEquals("jobs", result.jobTitles[0])
        assertEquals("knTitle", result.knownForTitles[0].title)
        assertEquals("tt5879411", result.knownForTitles[0].titleId?.value)
        assertEquals("1892", result.knownForTitles[0].releaseYear)
        assertEquals("knCover", result.knownForTitles[0].cover?.value)
        assertEquals("pdTitle", result.personalDetails[0].title)
        assertEquals("pdSubtitle", result.personalDetails[0].subtitle)
        assertEquals("linkText", result.personalDetails[0].links[0].title)
        assertEquals("", result.personalDetails[0].links[0].subtitle)
        assertEquals("linkTextUrl", result.personalDetails[0].links[0].url)
        assertEquals("", result.personalDetails[0].links[0].id)
        assertEquals("photoUrl", result.photos[0].image.value)
        assertEquals("photoID", result.photos[0].imageId?.value)
        assertEquals("rvCover", result.relatedVideos[0].preview.value)
        assertEquals("rvTitle", result.relatedVideos[0].title)
        assertEquals("vi5999987", result.relatedVideos[0].videoId.value)
        assertEquals("", result.relatedVideos[0].runtime)
        assertEquals("", result.trailer.runtime)
        assertEquals("vi4876655", result.trailer.videoId.value)
        assertEquals("tCaption", result.trailer.title)
        assertEquals("tCover", result.trailer.preview.value)



    }

}