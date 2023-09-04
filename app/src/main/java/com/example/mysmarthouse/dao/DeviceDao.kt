package com.example.mysmarthouse.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.mysmarthouse.models.Device

@Dao
interface DeviceDao {
    @Query("SELECT * FROM devices")
    suspend fun getDevices(): List<Device>

    @Upsert
    suspend fun upsertDevice(device: Device)

    @Query("SELECT * FROM devices WHERE tuya_id = :tuydId LIMIT 1")
    suspend fun findByTuyaId(tuydId: String): Device
}