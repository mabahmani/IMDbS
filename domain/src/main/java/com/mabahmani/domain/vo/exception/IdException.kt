package com.mabahmani.domain.vo.exception

sealed class IdException(message: String): Exception(message){
    data class InvalidIdFormatException(val id: String): IdException(id)
}