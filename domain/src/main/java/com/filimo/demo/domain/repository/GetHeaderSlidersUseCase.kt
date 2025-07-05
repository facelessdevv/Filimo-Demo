package com.filimo.demo.domain.repository

import kotlinx.coroutines.flow.Flow

interface ConnectivityRepository {
    fun observe(): Flow<Boolean>
}