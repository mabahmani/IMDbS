package com.mabahmani.data.vo.res

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NameBioResTest {
    private lateinit var model: NameBioRes

    @Before
    fun setup() {
        model = NameBioRes(
            "avatar",
            listOf(
                NameBioRes.Family(
                    "id",
                    "subtitle",
                    "title"
                )
            ),
            "miniBio",
            "name",
            listOf(
                NameBioRes.Overview(
                    "id",
                    "subtitle",
                    "title"
                )
            ),
            listOf(
                NameBioRes.Salary(
                    "id",
                    "subtitle",
                    "title"
                )
            ),
            listOf("trademark"),
            listOf("trivia")
        )
    }

    @Test
    fun `test NameBioRes model to NameBio domain model  conversion`() {
        val result = model.toNameBio()

        assertEquals("avatar", result.avatar.value)
        assertEquals("name", result.name)
        assertEquals("miniBio", result.miniBio)

        assertEquals("id", result.family[0].id)
        assertEquals("subtitle", result.family[0].subtitle)
        assertEquals("title", result.family[0].title)
        assertEquals("", result.family[0].url)

        assertEquals("id", result.overview[0].id)
        assertEquals("subtitle", result.overview[0].subtitle)
        assertEquals("title", result.overview[0].title)
        assertEquals("", result.overview[0].url)

        assertEquals("id", result.salary[0].id)
        assertEquals("subtitle", result.salary[0].subtitle)
        assertEquals("title", result.salary[0].title)
        assertEquals("", result.salary[0].url)

        assertEquals("trademark", result.trademark[0])
        assertEquals("trivia", result.trivia[0])


    }

}