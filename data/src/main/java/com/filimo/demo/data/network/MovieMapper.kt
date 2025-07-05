package com.filimo.demo.data.network

import com.filimo.demo.data.database.entity.MovieDbEntity
import com.filimo.demo.domain.model.HeaderSliderEntity
import com.filimo.demo.domain.model.MovieItemEntity
import com.filimo.demo.data.network.model.MovieApiResponse

fun MovieApiResponse.toMovieDbEntities(): List<MovieDbEntity> {
    return this.data
        .filter { it.output_type == "movie" && it.movies?.data?.isNotEmpty() == true }
        .flatMap { section ->
            section.movies!!.data.map { dto ->
                MovieDbEntity(
                    id = dto.id ?: dto.link_key ?: "",
                    title = dto.movie_title,
                    titleEn = dto.movie_title_en,
                    imageUrl = dto.cover_mobile,
                    rowTitle = section.link_text ?: "Featured",
                    description = dto.descr,
                    director = dto.director,
                    imdbRating = dto.imdb_rate,
                    year = dto.pro_year,
                    categories = dto.cat_title_str,
                    duration = dto.duration?.text,
                    pic_s = dto.pic?.movie_img_s,
                    pic_m = dto.pic?.movie_img_m,
                    pic_b = dto.pic?.movie_img_b
                )
            }
        }
}

fun MovieDbEntity.toDomainModel(): MovieItemEntity {
    return MovieItemEntity(
        id = this.id,
        title = this.title,
        titleEn = this.titleEn,
        imageUrl = this.imageUrl,
        rowTitle = this.rowTitle,
        description = this.description,
        director = this.director,
        imdbRating = this.imdbRating,
        year = this.year,
        categories = this.categories,
        duration = this.duration,
        pic_s = this.pic_s,
        pic_m = this.pic_m,
        pic_b = this.pic_b
    )
}

fun MovieApiResponse.toHeaderSliderEntities(): List<HeaderSliderEntity> {
    return data.firstOrNull { it.output_type == "headerslider" }
        ?.headersliders?.data?.map {
            HeaderSliderEntity(
                title = it.title,
                description = it.desc,
                mobileImageUrl = it.cover_mobile.firstOrNull(),
                desktopImageUrl = it.cover_desktop.firstOrNull()
            )
        } ?: emptyList()
}