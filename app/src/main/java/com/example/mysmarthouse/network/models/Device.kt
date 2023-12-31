package com.example.mysmarthouse.network.models

import com.google.gson.annotations.SerializedName

data class Device(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("icon")
    val icon: String,

    @SerializedName("category")
    val category: String
)