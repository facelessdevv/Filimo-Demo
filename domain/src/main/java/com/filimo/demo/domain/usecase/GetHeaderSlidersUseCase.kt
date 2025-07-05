package com.filimo.demo.domain.usecase

import com.filimo.demo.domain.model.HeaderSliderEntity
import com.filimo.demo.domain.repository.MovieRepository
import javax.inject.Inject

class GetHeaderSlidersUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): List<HeaderSliderEntity> {
        return movieRepository.getHeaderSliders()
    }
}