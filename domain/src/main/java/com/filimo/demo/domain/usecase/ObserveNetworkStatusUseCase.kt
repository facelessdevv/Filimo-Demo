package com.filimo.demo.domain.usecase

import com.filimo.demo.domain.repository.ConnectivityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveNetworkStatusUseCase @Inject constructor(
    private val connectivityRepository: ConnectivityRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return connectivityRepository.observe()
    }
}