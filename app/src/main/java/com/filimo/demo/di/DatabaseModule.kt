package com.filimo.demo.di

import android.content.Context
import androidx.room.Room
import com.filimo.demo.data.database.AppDatabase
import com.filimo.demo.data.database.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "movie_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    fun provideMovieDao(db: AppDatabase): MovieDao = db.movieDao()
}