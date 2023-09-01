package com.example.mysmarthouse.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "settings"
)
data class Setting(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "key") val key: String?,
    @ColumnInfo(name = "value") val value: String?
)