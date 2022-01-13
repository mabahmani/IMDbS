package com.mabahmani.data.vo.res

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class EventDetailsResTest{
    private lateinit var model: EventDetailsRes

    @Before
    fun setup(){
        model = EventDetailsRes(
            listOf(
                EventDetailsRes.Award(
                    "award name",
                    listOf(EventDetailsRes.Award.AwardCategory(
                        "award category name",
                        listOf(EventDetailsRes.Award.AwardCategory.AwardNomination(
                            "notes",
                            listOf(),
                            listOf(),
                            true,
                            listOf(
                                EventDetailsRes.Award.AwardCategory.AwardNomination.Nominee(
                                    "Nominee name",
                                    "notes",
                                    "image url",
                                    "nm56988"
                                )
                            ),
                            listOf()
                        ))
                    ))
                )
            ),
            "award title",
            "award subtitle",
            "2000"
        )
    }

    @Test
    fun `test EventDetailsRes model to EventDetails domain model  conversion`(){
        val result = model.toEventDetails()
        assertEquals("award title", result.eventName)
        assertEquals("award subtitle", result.eventCaption)
        assertEquals("2000", result.eventYear)
        assertEquals("award name",result.awards[0].awardName)
        assertEquals("award category name",result.awards[0].awardCategories[0].awardCategoryTitle)
        assertEquals("nm56988",result.awards[0].awardCategories[0].nominees[0].id)
        assertEquals("image url",result.awards[0].awardCategories[0].nominees[0].image.value)
        assertEquals("Nominee name",result.awards[0].awardCategories[0].nominees[0].name)
        assertEquals("notes",result.awards[0].awardCategories[0].nominees[0].note)
        assertTrue(result.awards[0].awardCategories[0].nominees[0].winner)
    }

}