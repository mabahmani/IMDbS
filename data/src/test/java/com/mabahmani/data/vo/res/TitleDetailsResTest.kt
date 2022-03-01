package com.mabahmani.data.vo.res

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TitleDetailsResTest {
    private lateinit var model: TitleDetailsRes

    @Before
    fun setup() {
        model = TitleDetailsRes(
            TitleDetailsRes.BoxOffice(
                "budget",
                "grossUs",
                "grossWorld",
                "openingWeekend"
            ),
            TitleDetailsRes.Details(
                listOf(
                    TitleDetailsRes.Details.CountryOfOrigin("coLink", "coTitle")
                ),
                listOf(
                    TitleDetailsRes.Details.FilmingLocation("flLink", "flTitle")
                ),
                listOf(
                    TitleDetailsRes.Details.Language("lanLink", "lanTitle")
                ),
                listOf(
                    TitleDetailsRes.Details.OfficialSite("offLink", "offTitle")
                ),
                listOf(
                    TitleDetailsRes.Details.ProductionCompany(
                        "companyLink", "companyTitle"
                    )
                ),
                listOf(
                    TitleDetailsRes.Details.ReleaseDate("realLink", "realTitle")
                )
            ),
            TitleDetailsRes.Overview(
                "cover",
                listOf(
                    TitleDetailsRes.Overview.Director(
                        "dirId",
                        "dirImage",
                        "dirLink",
                        "dirMovieName",
                        "dirRealName"
                    )
                ),
                listOf(
                    TitleDetailsRes.Overview.Genre("genreLink", "genreTitle")
                ),
                "5.6",
                "15689",
                "PG-13",
                "plot",
                "2004",
                "156",
                listOf(
                    TitleDetailsRes.Overview.Star(
                        "starId",
                        "starImage",
                        "starLink",
                        "starMovieName",
                        "starRealName"
                    )
                ),
                "title",
                "trailerDuration",
                "trailerPreview",
                "trailerVideoId",
                listOf(
                    TitleDetailsRes.Overview.Writer(
                        "writerId",
                        "writerImage",
                        "writerLink",
                        "writerMovieName",
                        "writerRealName"
                    )
                )
            ),
            listOf(
                TitleDetailsRes.Photo("originalPhoto", "thumbPhoto")
            ),
            listOf(
                TitleDetailsRes.RelatedMovie("rvCover", "rvId", "rvLink", "rvRate", "rvTitle")
            ),
            TitleDetailsRes.Storyline(
                listOf(TitleDetailsRes.Storyline.Genre("genreLink", "genreTitle")),
                listOf(TitleDetailsRes.Storyline.Keyword("keywordLink", "keywordTitle")),
                TitleDetailsRes.Storyline.MotionPictureRating("mpLink", "mpTitle"),
                TitleDetailsRes.Storyline.ParentsGuide("parentGuideLink", "parentGuidTitle"),
                "story",
                "tagline"
            ),
            listOf(
                TitleDetailsRes.TechnicalSpec("techSubtitle", "techTitle")
            ),
            listOf(
                TitleDetailsRes.TopCast(
                    "topCastId",
                    "topCastImage",
                    "topCastLink",
                    "topCastMovieName",
                    "topCastRealName"
                )
            ),
            TitleDetailsRes.TopReview(
                "topReviewDate",
                "topReviewRating",
                "topReview",
                "topReviewTitle",
                "topReviewUsername"
            ),
            listOf(
                TitleDetailsRes.Video(
                    "videoDuration",
                    "videoId",
                    "videoLink",
                    "videoPreview",
                    "videoTitle"
                )
            )
        )
    }

    @Test
    fun `test TitleDetailsRes model to TitleDetails domain model  conversion`() {
        val result = model.toTitleDetails()

        assertEquals("PG-13", result.certificate)
        assertEquals("cover", result.cover.value)
        assertEquals("5.6", result.imdbRating)
        assertEquals("title", result.name)
        assertEquals("15689", result.numberOfVotes)
        assertEquals("2004", result.releaseYear)
        assertEquals("156", result.runtime)
        assertEquals("plot", result.plot)

        assertEquals("budget", result.boxOffice.budget)
        assertEquals("grossUs", result.boxOffice.grossUsAndCanada)
        assertEquals("grossWorld", result.boxOffice.grossWorldwide)
        assertEquals("openingWeekend", result.boxOffice.openingWeekendUsAndCanada)

        assertEquals("coTitle", result.details.countryOfOrigin[0])
        assertEquals("flTitle", result.details.filmingLocations[0])
        assertEquals("lanTitle", result.details.languages[0])
        assertEquals("offTitle", result.details.officialSites[0])
        assertEquals("companyTitle", result.details.productionCompanies[0])
        assertEquals("realTitle", result.details.releaseDate[0])

        assertEquals("genreTitle", result.storyLine.genres[0])
        assertEquals("keywordTitle", result.storyLine.keywords[0])
        assertEquals("story", result.storyLine.story)
        assertEquals("tagline", result.storyLine.taglines)
        assertEquals("mpTitle", result.storyLine.motionPictureRating)

        assertEquals("topReviewDate", result.topReview.date)
        assertEquals("topReviewRating", result.topReview.rate)
        assertEquals("topReview", result.topReview.review)
        assertEquals("topReviewTitle", result.topReview.title)
        assertEquals("topReviewUsername", result.topReview.username)

        assertEquals("dirRealName", result.directors[0].name)
        assertEquals("dirId", result.directors[0].nameId.value)

        assertEquals("starRealName", result.stars[0].name)
        assertEquals("starId", result.stars[0].nameId.value)

        assertEquals("writerRealName", result.writers[0].name)
        assertEquals("writerId", result.writers[0].nameId.value)

        assertEquals("genreTitle", result.genres[0])

        assertEquals(null, result.photos[0].imageId)
        assertEquals("originalPhoto", result.photos[0].image.value)

        assertEquals("rvCover", result.relatedTitles[0].cover?.value)
        assertEquals("rvId", result.relatedTitles[0].titleId?.value)
        assertEquals("rvRate", result.relatedTitles[0].rate)
        assertEquals("rvTitle", result.relatedTitles[0].title)

        assertEquals("techTitle", result.technicalSpecs[0].title)
        assertEquals("techSubtitle", result.technicalSpecs[0].subtitle)

        assertEquals("topCastId", result.topCasts[0].nameId.value)
        assertEquals("topCastRealName", result.topCasts[0].name)
        assertEquals("topCastImage", result.topCasts[0].avatar.value)
        assertEquals("topCastMovieName", result.topCasts[0].description)

        assertEquals("videoPreview", result.videos[0].preview.value)
        assertEquals("videoTitle", result.videos[0].title)
        assertEquals("videoDuration", result.videos[0].runtime)
        assertEquals("videoId", result.videos[0].videoId.value)


    }

}