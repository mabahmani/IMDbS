package com.mabahmani.data.vo.res

import com.mabahmani.domain.vo.enum.HomeMediaType
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NameAwardResTest {
    private lateinit var model: NameAwardsRes

    @Before
    fun setup() {
        model = NameAwardsRes(
            "avatar",
            listOf(
                NameAwardsRes.Event(
                    listOf(
                        NameAwardsRes.Event.Award(
                            NameAwardsRes.Event.Award.Year(
                                "event id",
                                "1999"
                            ),
                            listOf(
                                NameAwardsRes.Event.Award.AwardOutcome(
                                    "award outcome name",
                                    "award outcome subtitle",
                                    listOf(
                                        NameAwardsRes.Event.Award.AwardOutcome.AwardDescription(
                                            "description",
                                            listOf(
                                                NameAwardsRes.Event.Award.AwardOutcome.AwardDescription.AwardItem(
                                                    "tt568998",
                                                    "award item title",
                                                    "2001"
                                                )
                                            )
                                        )
                                    )
                                )
                            )
                        )
                    ),
                    "event name"
                )
            ),
            "award name"
        )
    }

    @Test
    fun `test NameAwardsRes model to NameAwards domain model  conversion`() {
        val result = model.toNameAwards()

        assertEquals("avatar", result.avatar.value)
        assertEquals("award name", result.name)
        assertEquals("event name", result.events[0].name)
        assertEquals("event id", result.events[0].awards[0].eventId.value)
        assertEquals("1999", result.events[0].awards[0].eventYear)
        assertEquals("award outcome name", result.events[0].awards[0].awardOutcomes[0].title)
        assertEquals("award outcome subtitle", result.events[0].awards[0].awardOutcomes[0].subtitle)
        assertEquals("description", result.events[0].awards[0].awardOutcomes[0].awardDetails[0].description)
        assertEquals("tt568998", result.events[0].awards[0].awardOutcomes[0].awardDetails[0].awardTitles[0].titleLink.titleId.value)
        assertEquals("award item title", result.events[0].awards[0].awardOutcomes[0].awardDetails[0].awardTitles[0].titleLink.title)
        assertEquals("2001", result.events[0].awards[0].awardOutcomes[0].awardDetails[0].awardTitles[0].titleReleaseYear)

    }

}