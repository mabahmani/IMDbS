package com.mabahmani.domain.vo

import com.mabahmani.domain.vo.common.Id
import com.mabahmani.domain.vo.common.Image
import com.mabahmani.domain.vo.common.Video

data class Suggestion(
    val name:String,
    val caption: String,
    val year: String,
    val image: Image,
    val id: String,
    val videos: List<Video>
){
    fun evalId():Id{
        return if (id.startsWith("tt"))
            Id.TitleId
        else
            Id.NameId
    }
}
