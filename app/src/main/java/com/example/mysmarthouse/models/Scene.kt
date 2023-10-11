package com.example.mysmarthouse.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "scenes"
)
data class Scene(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "tuya_id") var tuyaId: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "enabled", defaultValue = "true") var enabled: Boolean = true,
    @ColumnInfo(name = "status", defaultValue = "1") var status: Int = 1
)
