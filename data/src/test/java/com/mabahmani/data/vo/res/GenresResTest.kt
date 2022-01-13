package com.mabahmani.data.vo.res

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GenresResTest{
    private lateinit var model: GenresRes

    @Before
    fun setup(){
        model = GenresRes(
            "genre name",
            "image url"
        )
    }

    @Test
    fun `test GenresRes model to Genre domain model  conversion`(){
        val result = model.toGenre()
        assertEquals("genre name", result.name)
        assertEquals("image url", result.image.value)
    }

}