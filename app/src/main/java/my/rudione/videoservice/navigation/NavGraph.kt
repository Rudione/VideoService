package my.rudione.videoservice.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import my.rudione.presentation.home.navigation.homeRoute

@Composable
fun SetupNavGraph(
    startDestination: String,
    navController: NavHostController
) {
    NavHost(
        startDestination = startDestination,
        navController = navController
    ) {
        homeRoute(onBackPressed = { navController.popBackStack() })
    }
}