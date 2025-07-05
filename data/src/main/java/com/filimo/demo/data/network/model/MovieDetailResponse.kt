package com.filimo.demo.data.network.model

import com.google.gson.annotations.SerializedName






data class DurationDto(
    val value: Int,
    val text: String?
)

data class MovieDetailResponse(
    val data: MovieDetailDto
)

data class MovieDetailDto(
    @SerializedName("movie_title") val title: String?,
    @SerializedName("movie_title_en") val titleEn: String?,
    val descr: String?,
    val director: String?,
    @SerializedName("pro_year") val year: String?,
    @SerializedName("imdb_rate") val imdbRate: String?,
    @SerializedName("cat_title_str") val categories: String?,
    val duration: DurationDto?,
    val pic: PicsDto?
)