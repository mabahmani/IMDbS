package com.mabahmani.data.vo.generic

data class ApiResponse<T>(
    val data: T,
    val success: Boolean,
    val error: String
)
