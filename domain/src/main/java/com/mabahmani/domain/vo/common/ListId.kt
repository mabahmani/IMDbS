package com.mabahmani.domain.vo.common

import com.mabahmani.domain.vo.exception.IdException

data class ListId(val value: String) {

    fun validate(): Either<IdException, Boolean> {
        return when {
            !"li[0-9]+".toRegex()
                .matches(value) -> Either.Left(IdException.InvalidIdFormatException(value))
            else -> Either.Right(true)
        }
    }
}
