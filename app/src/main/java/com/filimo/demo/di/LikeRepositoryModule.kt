package com.filimo.demo.di

import com.filimo.demo.domain.repository.LikeRepository
import com.filimo.demo.data.repository.LikeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LikeRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLikeRepository(
        impl: LikeRepositoryImpl
    ): LikeRepository

}
