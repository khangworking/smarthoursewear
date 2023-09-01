package com.example.mysmarthouse.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mysmarthouse.models.Setting

@Database(
    entities = [Setting::class],
    version = 1
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
                    ).allowMainThreadQueries().build()
                }
            }
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}