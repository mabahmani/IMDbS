package com.mabahmani.domain.vo.common

import com.mabahmani.domain.vo.exception.IdException

data class ListId(val value: String) {

    fun validate(id: String): Either<IdException, Boolean> {
        return when {
            !"li[0-9]+".toRegex()
                .matches(id) -> Either.Left(IdException.InvalidIdFormatException(id))
            else -> Either.Right(true)
        }
    }
}
