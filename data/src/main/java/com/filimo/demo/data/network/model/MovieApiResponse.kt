package com.filimo.demo.data.network.model

import com.google.gson.annotations.SerializedName

data class MovieApiResponse(
    val links: MovieLinks?,
    val data: List<MovieSection>
)

data class MovieLinks(
    val forward: String?
)

data class MovieSection(
    val output_type: String,
    val movies: MovieListWrapper?,
    val headersliders: HeaderSliders?,
    val link_text: String?
)

data class MovieListWrapper(
    val data: List<MovieDto>
)


data class MovieDto(
    val id: String?,
    val link_key: String?,
    val movie_title: String?,
    val movie_title_en: String?,
    val cover_mobile: String?,
    val pic: PicsDto? = null,

    val descr: String?,
    val director: String?,
    val imdb_rate: String?,
    val pro_year: String?,
    val cat_title_str: String?,
    val duration: DurationDto?,
    val cover: String?
)

data class PicsDto(
    val movie_img_s: String? = "",
    val movie_img_m: String? = "",
    val movie_img_b: String? = "",
)

data class HeaderSliders(
    val data: List<HeaderSlider>
)

data class HeaderSlider(
    val title: String,
    val desc: String,
    @SerializedName("cover") val cover_regular: List<String>,
    @SerializedName("cover_mobile") val cover_mobile: List<String>,
    @SerializedName("cover_desktop") val cover_desktop: List<String>
)

