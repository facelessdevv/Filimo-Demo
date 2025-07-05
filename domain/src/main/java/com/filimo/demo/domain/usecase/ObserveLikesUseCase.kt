package com.filimo.demo.domain.usecase

import com.filimo.demo.domain.repository.LikeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveLikesUseCase @Inject constructor(
    private val likeRepository: LikeRepository
) {
    operator fun invoke(): Flow<Set<String>> {
        return likeRepository.getLikedMovies().map { it.toSet() }
    }
}