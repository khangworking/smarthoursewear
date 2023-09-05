package com.example.mysmarthouse.network.models

data class Result<T>(
    val result: T?,
    val code: Int?,
    val msg: String?,
    val success: Boolean,
    val t: Long
)
