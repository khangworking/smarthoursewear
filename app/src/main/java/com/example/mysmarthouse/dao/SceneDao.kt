package com.example.mysmarthouse.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.mysmarthouse.models.Scene

@Dao
interface SceneDao {
    @Query("SELECT * FROM scenes")
    suspend fun getAllScenes(): List<Scene>

    @Upsert
    suspend fun upsertScene(scene: Scene)

    @Query("SELECT * FROM scenes WHERE tuya_id LIKE :id LIMIT 1")
    suspend fun findByTuyaId(id: String): Scene

    @Query("DELETE FROM scenes")
    suspend fun deleteAll()
}