package com.eltonjhony.eventapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.eltonjhony.eventapp.data.local.entities.EventEntity
import com.eltonjhony.eventapp.data.local.entities.ImageEntity
import com.eltonjhony.eventapp.data.local.entities.LocationEntity

@Database(
    entities = [EventEntity::class, LocationEntity::class, ImageEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun eventDao(): EventDao

    companion object {

        fun getDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "events.db"
            ).build()
        }

    }

}