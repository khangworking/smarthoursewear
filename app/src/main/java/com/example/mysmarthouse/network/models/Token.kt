package com.example.mysmarthouse.network.models

data class Token(
    val access_token: String,
    val expire_time: Int,
    val refresh_token: String,
    val uid: String
)
