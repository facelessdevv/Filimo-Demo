package com.filimo.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.filimo.demo.ui.movieexplorer.MovieDetailScreen
import com.filimo.demo.ui.movieexplorer.MovieExplorerScreen
import com.filimo.demo.ui.theme.FilimoTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            FilimoTheme {
                val systemUiController = rememberSystemUiController()

                val useDarkIcons = !isSystemInDarkTheme()

                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = Color.Transparent,
                        darkIcons = useDarkIcons
                    )
                }

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "movie_list"
                ) {
                    composable("movie_list") {
                        MovieExplorerScreen(
                            onMovieClick = { movieId ->
                                navController.navigate("movie_detail/$movieId")
                            }
                        )
                    }

                    composable("movie_detail/{movieId}") {

                        MovieDetailScreen(navController = navController)
                    }
                }
            }
        }
    }
}