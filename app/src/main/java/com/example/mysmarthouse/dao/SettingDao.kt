package com.example.mysmarthouse.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.mysmarthouse.models.Setting

@Dao
interface SettingDao {
    @Query("SELECT * FROM settings")
    suspend fun getAll(): List<Setting>

    @Query("SELECT * FROM settings WHERE key LIKE :key")
    suspend fun find(key: String): Setting

    @Upsert
    suspend fun upsertSetting(setting: Setting)
}