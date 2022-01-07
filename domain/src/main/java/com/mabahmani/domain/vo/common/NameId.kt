package com.mabahmani.domain.vo.common

import com.mabahmani.domain.vo.exception.IdException

data class NameId(val value: String) {

    fun validate(id: String): Either<IdException, Boolean> {
        return when {
            !"nm[0-9]+".toRegex()
                .matches(id) -> Either.Left(IdException.InvalidIdFormatException(id))
            else -> Either.Right(true)
        }
    }
}
