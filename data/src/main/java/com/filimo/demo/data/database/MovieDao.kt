package com.filimo.demo.data.database

import androidx.room.*
import com.filimo.demo.data.database.entity.LikedMovie
import com.filimo.demo.data.database.entity.MovieDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM liked_movies")
    fun getLiked(): Flow<List<LikedMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun like(movie: LikedMovie)

    @Delete
    suspend fun unlike(movie: LikedMovie)

    @Query("SELECT EXISTS(SELECT 1 FROM liked_movies WHERE movieId = :movieId)")
    suspend fun isLiked(movieId: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(movies: List<MovieDbEntity>)

    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<MovieDbEntity>>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovieById(movieId: String): Flow<MovieDbEntity?>

    @Query("DELETE FROM movies")
    suspend fun clearAllMovies()
}