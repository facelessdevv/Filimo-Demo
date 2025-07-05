package com.filimo.demo.data.repository

import com.filimo.demo.domain.common.Constants
import com.filimo.demo.data.database.MovieDao
import com.filimo.demo.data.network.FilimoApi
import com.filimo.demo.data.network.toHeaderSliderEntities
import com.filimo.demo.data.network.toMovieDbEntities
import com.filimo.demo.data.network.toDomainModel
import com.filimo.demo.domain.model.HeaderSliderEntity
import com.filimo.demo.domain.model.MovieItemEntity
import com.filimo.demo.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val api: FilimoApi,
    private val dao: MovieDao
) : MovieRepository {

    private var nextUrl: String? = null
    private var isFetching = false

    override fun getMovies(): Flow<List<MovieItemEntity>> {
        return dao.getAllMovies().map { dbEntities ->
            dbEntities.map { it.toDomainModel() }
        }
    }

    override suspend fun fetchNextMovies() {
        if (isFetching) return
        isFetching = true
        try {
            val urlToFetch = nextUrl ?: Constants.INITIAL_MOVIE_LIST_URL
            val response = api.fetchMovies(urlToFetch)
            val movies = response.toMovieDbEntities()
            if (nextUrl == null) {
                dao.clearAllMovies()
            }
            dao.insertAllMovies(movies)
            nextUrl = response.links?.forward
            if (nextUrl == null) {
                nextUrl = Constants.INITIAL_MOVIE_LIST_URL
            }
        } finally {
            isFetching = false
        }
    }

    override suspend fun getHeaderSliders(): List<HeaderSliderEntity> {
        return try {
            api.fetchHeaderSliders().toHeaderSliderEntities()
        } catch (e: Exception) {
            emptyList()
        }
    }


    override fun getMovieDetail(movieId: String): Flow<MovieItemEntity?> {
        return dao.getMovieById(movieId).map { it?.toDomainModel() }
    }
}