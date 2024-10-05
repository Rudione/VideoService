package my.rudione.presentation.home.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import my.rudione.presentation.Screen
import my.rudione.presentation.home.HomeScreen
import my.rudione.presentation.home.HomeViewModel

fun NavGraphBuilder.homeRoute(
    onBackPressed: () -> Unit
) {
    composable(route = Screen.Home.route) {
        val homeViewModel = hiltViewModel<HomeViewModel>()

        HomeScreen(homeViewModel = homeViewModel)
    }
}