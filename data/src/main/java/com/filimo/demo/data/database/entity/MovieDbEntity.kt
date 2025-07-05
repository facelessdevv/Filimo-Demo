package com.filimo.demo.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieDbEntity(
    @PrimaryKey val id: String,
    val title: String?,
    val titleEn: String?,
    val imageUrl: String?,
    val rowTitle: String?,
    val description: String?,
    val director: String?,
    val imdbRating: String?,
    val year: String?,
    val categories: String?,
    val duration: String?,
    val pic_s: String?,
    val pic_m: String?,
    val pic_b: String?
)