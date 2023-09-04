package com.example.mysmarthouse.dao

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mysmarthouse.models.Device
import com.example.mysmarthouse.models.Setting

@Database(
    entities = [Setting::class, Device::class],
    version = 4,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(3,4)
    ]
)
abstract class HouseDatabase: RoomDatabase() {
    abstract val dao: SettingDao
    abstract val deviceDao: DeviceDao

    companion object {
        private var INSTANCE: HouseDatabase? = null

        fun getInstance(context: Context): HouseDatabase {
            if (INSTANCE == null) {
                synchronized(HouseDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        HouseDatabase::class.java,
                        "HouseDB"
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE!!
        }
    }
}