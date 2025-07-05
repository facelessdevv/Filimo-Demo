package com.filimo.demo.di

import com.filimo.demo.data.common.NetworkConnectivityObserver
import com.filimo.demo.domain.repository.ConnectivityRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindConnectivityRepository(
        impl: NetworkConnectivityObserver
    ): ConnectivityRepository
}