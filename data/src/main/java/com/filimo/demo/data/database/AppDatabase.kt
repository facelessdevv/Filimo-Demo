package com.filimo.demo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.filimo.demo.data.database.entity.LikedMovie
import com.filimo.demo.data.database.entity.MovieDbEntity

@Database(
    entities = [LikedMovie::class, MovieDbEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}