package com.filimo.demo.data.repository

import com.filimo.demo.data.database.MovieDao
import com.filimo.demo.data.database.entity.LikedMovie
import com.filimo.demo.domain.repository.LikeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LikeRepositoryImpl @Inject constructor(
    private val dao: MovieDao
) : LikeRepository {
    override fun getLikedMovies(): Flow<List<String>> =
        dao.getLiked().map { list -> list.map { it.movieId } }

    override suspend fun toggleLike(movieId: String) {
        val liked = dao.isLiked(movieId)
        if (liked) dao.unlike(LikedMovie(movieId))
        else dao.like(LikedMovie(movieId))
    }
}