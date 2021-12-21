package com.mabahmani.data.vo.res

import com.mabahmani.domain.vo.Suggestion
import com.mabahmani.domain.vo.common.Image
import com.mabahmani.domain.vo.common.Video
import com.mabahmani.domain.vo.common.VideoId

data class SuggestRes(
    val d: List<D>,
    val q: String,
    val v: Int
) {
    data class D(
        val i: I,
        val id: String,
        val l: String,
        val q: String,
        val rank: Int,
        val s: String,
        val v: List<V>,
        val vt: Int,
        val y: Int
    ) {
        data class I(
            val height: Int,
            val imageUrl: String,
            val width: Int
        )

        data class V(
            val i: I,
            val id: String,
            val l: String,
            val s: String
        ) {
            data class I(
                val height: Int,
                val imageUrl: String,
                val width: Int
            )
        }
    }

    fun toSuggestion(): List<Suggestion>{
        return d.map {
            Suggestion(
                it.l,
                it.s,
                it.y.toString(),
                Image(it.i.imageUrl),
                it.id,
                it.v.map {
                    Video(
                        VideoId(it.id),
                        Image(it.i.imageUrl),
                        it.l,
                        it.s
                    )
                }
            )
        }
    }
}