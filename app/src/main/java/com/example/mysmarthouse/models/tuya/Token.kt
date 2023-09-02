package com.example.mysmarthouse.models.tuya

data class Token(
    val access_token: String,
    val expire_time: Int,
    val refresh_token: String,
    val uid: String
)
