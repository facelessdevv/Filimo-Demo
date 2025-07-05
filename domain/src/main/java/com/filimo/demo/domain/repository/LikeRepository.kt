package com.filimo.demo.domain.repository

import kotlinx.coroutines.flow.Flow

interface LikeRepository {
    fun getLikedMovies(): Flow<List<String>>
    suspend fun toggleLike(movieId: String)
}
