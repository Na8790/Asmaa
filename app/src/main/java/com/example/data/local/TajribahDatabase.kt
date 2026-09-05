package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.Booking
import com.example.data.model.Experience
import com.example.data.model.Review
import com.example.data.model.Trip

@Database(
    entities = [Experience::class, Trip::class, Booking::class, Review::class],
    version = 1,
    exportSchema = false
)
abstract class TajribahDatabase : RoomDatabase() {
    abstract fun dao(): TajribahDao

    companion object {
        @Volatile
        private var INSTANCE: TajribahDatabase? = null

        fun getDatabase(context: Context): TajribahDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TajribahDatabase::class.java,
                    "tajribah_platform_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
