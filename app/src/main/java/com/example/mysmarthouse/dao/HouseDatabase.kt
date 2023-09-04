package com.example.mysmarthouse.dao

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mysmarthouse.models.Setting

@Database(
    entities = [Setting::class],
    version = 3,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(2,3)
    ]
)
abstract class HouseDatabase: RoomDatabase() {
    abstract val dao: SettingDao

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