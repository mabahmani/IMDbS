package com.mabahmani.domain.vo.common

import com.mabahmani.domain.vo.exception.IdException

data class TitleId(val value: String) {

    fun validate(id: String): Either<IdException, Boolean> {
        return when {
            !"tt[0-9]+".toRegex()
                .matches(id) -> Either.Left(IdException.InvalidIdFormatException(id))
            else -> Either.Right(true)
        }
    }
}
