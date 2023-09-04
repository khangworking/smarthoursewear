package com.example.mysmarthouse.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "devices"
)
data class Device(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "tuya_id") var tuyaId: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "icon") var icon: String
)