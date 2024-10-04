package my.rudione.presentation

sealed class Screen(
    val route: String
) {
    data object Home : Screen("home_screen")
}