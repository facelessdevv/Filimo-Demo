package com.filimo.demo.ui.movieexplorer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.filimo.demo.presentation.movieexplorer.MovieDetailViewModel
import com.filimo.demo.util.isRunningOnTv

@Composable
fun MovieDetailScreen(
    navController: NavController,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    val movie by viewModel.movie.collectAsState()
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
    val backgroundColor = MaterialTheme.colorScheme.background
    val isTv = isRunningOnTv()
    val headerHeight = if (isTv) 500.dp else 350.dp

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            val currentMovie = movie
            if (currentMovie == null) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(headerHeight)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(currentMovie.pic_b ?: currentMovie.imageUrl),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            backgroundColor
                                        )
                                    )
                                )
                        )
                        IconButton(
                            onClick = { navController.navigateUp() },
                            modifier = Modifier
                                .padding(top = systemBarsPadding.calculateTopPadding(), end = 16.dp)
                                .align(Alignment.TopEnd)
                                .clip(CircleShape)
                                .background(Color.Black.copy(alpha = 0.4f))
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .offset(y = (-50).dp)
                    ) {
                        Text(
                            text = currentMovie.title ?: "عنوان نامشخص",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = currentMovie.titleEn ?: "Unknown Title",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 24.dp)
                        ) {
                            val detailColor = MaterialTheme.colorScheme.onBackground
                            currentMovie.imdbRating?.let { Text(text = "IMDb ${it}", fontWeight = FontWeight.SemiBold, color = detailColor) }
                            currentMovie.year?.let { Text(text = it, color = detailColor) }
                            currentMovie.duration?.let { Text(text = it, color = detailColor) }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Button(
                                onClick = { /*TODO*/ },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Play")
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "تماشا")
                            }
                            OutlinedButton(onClick = { /*TODO*/ }) {
                                Icon(imageVector = Icons.Default.Favorite, contentDescription = "Like", tint = MaterialTheme.colorScheme.primary)
                            }
                            OutlinedButton(onClick = { /*TODO*/ }) {
                                Icon(imageVector = Icons.Default.Share, contentDescription = "Share", tint = MaterialTheme.colorScheme.primary)
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = currentMovie.description ?: "توضیحات موجود نیست.",
                            style = MaterialTheme.typography.bodyLarge,
                            lineHeight = 28.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        val secondaryDetailColor = MaterialTheme.colorScheme.onSurfaceVariant
                        currentMovie.director?.let {
                            Text(text = "کارگردان: $it", style = MaterialTheme.typography.bodyMedium, color = secondaryDetailColor)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        currentMovie.categories?.let {
                            Text(text = "ژانر: $it", style = MaterialTheme.typography.bodyMedium, color = secondaryDetailColor)
                        }
                    }
                    Spacer(modifier = Modifier.height(systemBarsPadding.calculateBottomPadding() + 16.dp))
                }
            }
        }
    }
}

