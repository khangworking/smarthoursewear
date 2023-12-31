package com.example.mysmarthouse.network.models

import com.google.gson.annotations.SerializedName

data class Scene(
    @SerializedName("scene_id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("enabled")
    val enabled: Boolean,

    @SerializedName("status")
    val status: Int
)
