package com.ys.jetmovieapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ys.jetmovieapp.MyApp
import com.ys.jetmovieapp.screens.home.HomeScreen
import com.ys.jetmovieapp.screens.details.DetailScreen

@Composable
fun MovieNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MovieScreens.HomeScreen.name
    ) {
        composable(MovieScreens.HomeScreen.name) {
            // here we pass where this should lead us to
             HomeScreen(navController = navController)
        }

        composable(MovieScreens.DetailsScreen.name) {
            DetailScreen(navController = navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp {
        MovieNavigation()
    }
}