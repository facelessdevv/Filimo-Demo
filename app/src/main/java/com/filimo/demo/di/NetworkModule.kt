package com.filimo.demo.di

import android.util.Log
import com.filimo.demo.domain.common.Constants
import com.filimo.demo.data.network.FilimoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {

        val logging = HttpLoggingInterceptor { message ->
            Log.d("OkHttp", message)
        }
        logging.level = HttpLoggingInterceptor.Level.BODY


        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideFilimoApi(retrofit: Retrofit): FilimoApi =
        retrofit.create(FilimoApi::class.java)
}