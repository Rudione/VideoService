package my.rudione.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
    Text(
        text = "Home Screen",
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    )
}