package my.rudione.presentation.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import my.rudione.presentation.Screen
import my.rudione.presentation.home.HomeScreen

fun NavGraphBuilder.homeRoute() {
    composable(route = Screen.Home.route) {
        HomeScreen()
    }
}