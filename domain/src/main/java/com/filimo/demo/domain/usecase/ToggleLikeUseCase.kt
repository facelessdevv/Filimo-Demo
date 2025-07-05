package com.filimo.demo.domain.usecase

import com.filimo.demo.domain.repository.LikeRepository
import javax.inject.Inject

class ToggleLikeUseCase @Inject constructor(
    private val likeRepository: LikeRepository
) {
    suspend operator fun invoke(movieId: String) {
        likeRepository.toggleLike(movieId)
    }
}