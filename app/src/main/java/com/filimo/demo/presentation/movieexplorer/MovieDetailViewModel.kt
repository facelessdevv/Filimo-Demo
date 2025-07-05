package com.filimo.demo.presentation.movieexplorer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.filimo.demo.domain.model.MovieItemEntity
import com.filimo.demo.domain.usecase.GetMovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _movie = MutableStateFlow<MovieItemEntity?>(null)
    val movie: StateFlow<MovieItemEntity?> = _movie

    init {
        val movieId = savedStateHandle.get<String>("movieId")
        if (movieId != null) {
            viewModelScope.launch {
                getMovieDetailUseCase(movieId).collect { movieFromDb ->
                    _movie.value = movieFromDb
                }
            }
        }
    }
}