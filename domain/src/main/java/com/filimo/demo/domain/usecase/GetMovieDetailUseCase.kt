package com.filimo.demo.domain.usecase

import com.filimo.demo.domain.model.MovieItemEntity
import com.filimo.demo.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(movieId: String): Flow<MovieItemEntity?> {
        return movieRepository.getMovieDetail(movieId)
    }
}