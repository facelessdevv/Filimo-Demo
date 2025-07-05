package com.filimo.demo.ui.movieexplorer

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.SignalWifiOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.filimo.demo.domain.model.HeaderSliderEntity
import com.filimo.demo.domain.model.MovieItemEntity
import com.filimo.demo.presentation.movieexplorer.MovieExplorerViewModel
import com.filimo.demo.util.isRunningOnTv

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieExplorerScreen(
    viewModel: MovieExplorerViewModel = hiltViewModel(),
    onMovieClick: (String) -> Unit
) {
    val movieRows by viewModel.movieRows.collectAsState()
    val likedMovies by viewModel.likes.collectAsState()
    val headerSliders by viewModel.headerSliders.collectAsState()
    val isOnline by viewModel.isOnline.collectAsState()
    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()

    LaunchedEffect(headerSliders) {
        if (headerSliders.isNotEmpty()) {
            kotlinx.coroutines.delay(100)
            try {
                focusRequester.requestFocus()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    LaunchedEffect(isOnline) {
        if (!isOnline && movieRows.isNotEmpty()) {
            Toast.makeText(context, "اتصال به اینترنت برقرار نیست", Toast.LENGTH_LONG).show()
        }
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (!isOnline && movieRows.isEmpty()) {
                OfflineState()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = systemBarsPadding.calculateBottomPadding() + 16.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(systemBarsPadding.calculateTopPadding()))
                    }

                    if (headerSliders.isNotEmpty()) {
                        item {
                            val pagerState = rememberPagerState(pageCount = { headerSliders.size })
                            val isTv = isRunningOnTv()
                            val sliderHeight = if (isTv) 350.dp else 200.dp

                            HorizontalPager(
                                state = pagerState,
                                contentPadding = PaddingValues(horizontal = 24.dp),
                                pageSpacing = 16.dp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(sliderHeight)
                            ) { page ->
                                val sliderModifier = Modifier
                                    .then(
                                        if (page == 0) {
                                            Modifier
                                                .focusRequester(focusRequester)
                                                .focusable()
                                        } else Modifier
                                    )
                                HeaderSliderItem(
                                    slider = headerSliders[page],
                                    modifier = sliderModifier
                                )
                            }
                        }
                    }

                    items(movieRows, key = { it.title }) { row ->
                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn(animationSpec = tween(durationMillis = 500)),
                            exit = fadeOut(animationSpec = tween(durationMillis = 500))
                        ) {
                            Column(modifier = Modifier.animateItemPlacement()) {
                                Text(
                                    text = row.title,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                LazyRow(
                                    contentPadding = PaddingValues(horizontal = 16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    items(row.movies, key = { it.id }) { movie ->
                                        MovieCard(
                                            movie = movie,
                                            isLiked = likedMovies.contains(movie.id),
                                            onLikeClick = { viewModel.toggleLike(movie.id) },
                                            onMovieClick = { onMovieClick(movie.id) }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun BoxScope.OfflineState() {
    Column(
        modifier = Modifier.align(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.SignalWifiOff,
            contentDescription = "Offline",
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "اتصال به اینترنت برقرار نیست",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "لطفا اتصال خود را بررسی کنید",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun HeaderSliderItem(slider: HeaderSliderEntity, modifier: Modifier = Modifier) {
    var isFocused by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(targetValue = if (isFocused) 1.05f else 1.0f, label = "scale")
    val borderColor by animateDpAsState(targetValue = if (isFocused) 3.dp else 0.dp, label = "border")
    val isTv = isRunningOnTv()
    val imageUrl = if (isTv) slider.desktopImageUrl else slider.mobileImageUrl

    Card(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .scale(scale)
            .onFocusChanged { isFocused = it.isFocused }
            .focusable()
            .border(
                width = borderColor,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = slider.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .drawWithContent {
                        val gradient = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = size.height / 2,
                            endY = size.height
                        )
                        drawContent()
                        drawRect(brush = gradient, blendMode = BlendMode.Multiply)
                    }
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = slider.title ?: "",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = slider.description ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun MovieCard(
    movie: MovieItemEntity,
    isLiked: Boolean,
    onLikeClick: () -> Unit,
    onMovieClick: () -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(targetValue = if (isFocused) 1.1f else 1.0f, label = "scale")
    val elevation by animateDpAsState(targetValue = if (isFocused) 12.dp else 4.dp, label = "elevation")

    Column(
        modifier = Modifier
            .width(160.dp)
            .scale(scale)
            .onFocusChanged { isFocused = it.isFocused }
            .focusable()
            .clickable { onMovieClick() }
    ) {
        Box {
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = elevation),
                shape = RoundedCornerShape(12.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(movie.pic_s),
                    contentDescription = movie.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2f / 3f),
                    contentScale = ContentScale.Crop
                )
            }
            IconButton(
                onClick = onLikeClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.4f))
            ) {
                Icon(
                    imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Like",
                    tint = if (isLiked) MaterialTheme.colorScheme.primary else Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = movie.title ?: "بدون عنوان",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = movie.titleEn ?: "No Title",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
