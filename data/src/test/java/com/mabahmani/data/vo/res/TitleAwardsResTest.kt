package com.mabahmani.data.vo.res

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TitleAwardsResTest {
    private lateinit var model: TitleAwardsRes

    @Before
    fun setup() {
        model = TitleAwardsRes(
            "cover",
            listOf(
                TitleAwardsRes.Event(
                    listOf(TitleAwardsRes.Event.Award(
                        "awardCategory",
                        listOf(
                            TitleAwardsRes.Event.Award.AwardDescription(
                                listOf(
                                    TitleAwardsRes.Event.Award.AwardDescription.AwardItem(
                                        "award Item ID",
                                        "award Item Title"
                                    )
                                ),
                                "note",
                                "award Description Title"
                            )
                        ),
                        "award outcome"
                    )),
                    "ev56498989",
                    "event title",
                    "1987"
                )
            ),
            "title",
            "1903"
        )
    }

    @Test
    fun `test TitleAwardsRes model to TitleAward domain model  conversion`() {
        val result = model.toTitleAward()

        assertEquals("cover", result.cover.value)
        assertEquals("title", result.name)
        assertEquals("event title", result.events[0].name)
        assertEquals("1987", result.events[0].awards[0].eventYear)
        assertEquals("ev56498989", result.events[0].awards[0].eventId.value)
        assertEquals("awardCategory", result.events[0].awards[0].awardOutcomes[0].title)
        assertEquals("award outcome", result.events[0].awards[0].awardOutcomes[0].subtitle)
        assertEquals("award Description Title", result.events[0].awards[0].awardOutcomes[0].awardDetails[0].description)
        assertEquals("award Item Title", result.events[0].awards[0].awardOutcomes[0].awardDetails[0].awardNames[0].name)
        assertEquals("award Item ID", result.events[0].awards[0].awardOutcomes[0].awardDetails[0].awardNames[0].nameId.value)



    }

}