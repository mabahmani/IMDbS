package com.mabahmani.domain.vo.exception

import java.lang.Exception

sealed class IdException(message: String): Exception(message){
    data class InvalidIdFormatException(val id: String): IdException(id)
}