package com.example.mysmarthouse.network.models

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("expire_time")
    val expireTime: Int
)
