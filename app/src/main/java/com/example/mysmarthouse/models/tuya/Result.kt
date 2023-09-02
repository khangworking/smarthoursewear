package com.example.mysmarthouse.models.tuya

data class Result<T>(
    val result: T?,
    val code: Int?,
    val msg: String?,
    val success: Boolean,
    val t: Long,
    val tid: String
)
