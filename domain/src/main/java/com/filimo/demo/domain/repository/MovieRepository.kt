package com.filimo.demo.domain.repository

import com.filimo.demo.domain.model.HeaderSliderEntity
import com.filimo.demo.domain.model.MovieItemEntity
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMovies(): Flow<List<MovieItemEntity>>


    suspend fun fetchNextMovies()

    suspend fun getHeaderSliders(): List<HeaderSliderEntity>
    fun getMovieDetail(movieId: String): Flow<MovieItemEntity?>
}