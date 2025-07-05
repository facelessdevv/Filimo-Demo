package com.filimo.demo.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "liked_movies")
data class LikedMovie(
    @PrimaryKey val movieId: String
)