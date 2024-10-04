package my.rudione.presentation.home.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import my.rudione.presentation.Screen
import my.rudione.presentation.home.HomeScreen
import my.rudione.presentation.home.HomeViewModel
import my.rudione.presentation.home.VideoEvent

fun NavGraphBuilder.homeRoute() {
    composable(route = Screen.Home.route) {
        val homeViewModel = hiltViewModel<HomeViewModel>()

        LaunchedEffect(Unit) {
            homeViewModel.onEvent(VideoEvent.LoadVideos)
        }

        HomeScreen(homeViewModel = homeViewModel)
    }
}