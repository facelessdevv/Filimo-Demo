package com.filimo.demo.domain.usecase

import com.filimo.demo.domain.model.MovieRow
import com.filimo.demo.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMovieRowsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(): Flow<List<MovieRow>> {
        return movieRepository.getMovies().map { moviesFromDb ->
            moviesFromDb
                .groupBy { it.rowTitle }
                .map { (title, movieList) ->
                    MovieRow(title ?: "Featured", movieList)
                }
        }
    }
}