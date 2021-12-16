package com.mabahmani.domain.vo.common

sealed class Either <out L, out R>{
    data class Left<L>(val value: L): Either<L, Nothing>()
    data class Right<R>(val value: R): Either<Nothing, R>()
}
