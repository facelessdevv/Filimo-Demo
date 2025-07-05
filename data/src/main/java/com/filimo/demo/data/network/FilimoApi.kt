package com.filimo.demo.data.network

import com.filimo.demo.data.network.model.MovieApiResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Url

interface FilimoApi {
    @GET
    suspend fun fetchMovies(
        @Url url: String,
        @Header("JsonType") jsonType: String = "simple"
    ): MovieApiResponse


    @GET("api/fa/v1/movie/movie/list/tagid/1/movie_id/{movieId}")
    suspend fun fetchMovieDetail(
        @Path("movieId") movieId: String,
        @Header("JsonType") jsonType: String = "simple"
    ): MovieApiResponse

    @GET("api/fa/v1/movie/movie/list/tagid/1")
    suspend fun fetchHeaderSliders(
        @Header("JsonType") jsonType: String = "simple"
    ): MovieApiResponse
}