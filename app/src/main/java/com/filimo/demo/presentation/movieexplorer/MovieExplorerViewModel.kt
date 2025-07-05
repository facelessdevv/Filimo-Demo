package com.filimo.demo.presentation.movieexplorer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filimo.demo.domain.model.HeaderSliderEntity
import com.filimo.demo.domain.model.MovieRow
import com.filimo.demo.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.delay

private const val REFRESH_INTERVAL_MS = 10000L

@HiltViewModel
class MovieExplorerViewModel @Inject constructor(
    getMovieRowsUseCase: GetMovieRowsUseCase,
    observeLikesUseCase: ObserveLikesUseCase,
    private val toggleLikeUseCase: ToggleLikeUseCase,
    private val getHeaderSlidersUseCase: GetHeaderSlidersUseCase,
    private val fetchNextMoviesUseCase: FetchNextMoviesUseCase,
    observeNetworkStatusUseCase: ObserveNetworkStatusUseCase
) : ViewModel() {

    private val _movieRows = MutableStateFlow<List<MovieRow>>(emptyList())
    val movieRows: StateFlow<List<MovieRow>> = _movieRows.asStateFlow()

    private val _headerSliders = MutableStateFlow<List<HeaderSliderEntity>>(emptyList())
    val headerSliders: StateFlow<List<HeaderSliderEntity>> = _headerSliders.asStateFlow()

    // Likes are observed directly from the usecase's Flow
    val likes: StateFlow<Set<String>> = observeLikesUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    val isOnline = observeNetworkStatusUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = true
        )

    init {
        // Observe movie rows
        viewModelScope.launch {
            getMovieRowsUseCase().collect { rows ->
                _movieRows.value = rows
            }
        }

        // Fetch initial data and schedule periodic fetching
        viewModelScope.launch {
            if (isOnline.value) {
                _headerSliders.value = getHeaderSlidersUseCase()
            }

            while (true) {
                if (isOnline.value) {
                    fetchNextMoviesUseCase()
                }
                delay(REFRESH_INTERVAL_MS)
            }
        }
    }

    fun toggleLike(movieId: String) {
        viewModelScope.launch {
            toggleLikeUseCase(movieId)
        }
    }
}