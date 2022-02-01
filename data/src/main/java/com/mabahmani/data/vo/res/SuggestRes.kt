package com.mabahmani.data.vo.res

import com.mabahmani.domain.util.orZero
import com.mabahmani.domain.vo.Suggestion
import com.mabahmani.domain.vo.common.Image
import com.mabahmani.domain.vo.common.Video
import com.mabahmani.domain.vo.common.VideoId

data class SuggestRes(
    val d: List<D>?,
    val q: String?,
    val v: Int?
) {
    data class D(
        val i: I?,
        val id: String?,
        val l: String?,
        val q: String?,
        val rank: Int?,
        val s: String?,
        val v: List<V>?,
        val vt: Int?,
        val y: Int?
    ) {
        data class I(
            val height: Int?,
            val imageUrl: String?,
            val width: Int?
        )

        data class V(
            val i: I?,
            val id: String?,
            val l: String?,
            val s: String?
        ) {
            data class I(
                val height: Int?,
                val imageUrl: String?,
                val width: Int?
            )
        }
    }

    fun toSuggestion(): List<Suggestion>{
        return d?.map {
            Suggestion(
                it.l.orEmpty(),
                it.s.orEmpty(),
                it.y.orZero().toString(),
                Image(it.i?.imageUrl.orEmpty()),
                it.id.orEmpty(),
                it.v?.map {
                    Video(
                        VideoId(it.id.orEmpty()),
                        Image(it.i?.imageUrl.orEmpty()),
                        it.l.orEmpty(),
                        it.s.orEmpty()
                    )
                }?: listOf()
            )
        } ?: listOf()
    }
}