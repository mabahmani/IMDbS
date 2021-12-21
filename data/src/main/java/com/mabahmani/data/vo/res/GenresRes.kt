package com.mabahmani.data.vo.res

import com.mabahmani.domain.vo.common.Genre
import com.mabahmani.domain.vo.common.Image

data class GenresRes(
    val genre: String,
    val image: String
){
    fun toGenre(): Genre{
        return Genre(genre, Image(image))
    }
}