package com.filimo.demo.domain.usecase

import com.filimo.demo.domain.repository.MovieRepository
import javax.inject.Inject

class FetchNextMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke() {
        movieRepository.fetchNextMovies()
    }
}