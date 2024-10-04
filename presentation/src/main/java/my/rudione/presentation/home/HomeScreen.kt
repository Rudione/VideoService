package my.rudione.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import my.rudione.presentation.components.VideoPlayer

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    videoState: VideoState,
    isVideoPlaying: Boolean,
    currentVideoSource: String
) {
    var isPlaying by remember { mutableStateOf(false) }
    var videoUrl by remember { mutableStateOf("") }

    Scaffold(
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                if (isPlaying) {

                    VideoPlayer(videoUrl = videoUrl, onClose = {
                        isPlaying = false
                    })
                } else {

                    HomeContent(
                        videoState = videoState,
                        onEvent = { event ->
                            homeViewModel.onEvent(event)
                        },
                        isVideoPlaying = isVideoPlaying,
                        currentVideoSource = currentVideoSource
                    ) { video ->
                        videoUrl = video.sources.first()
                        isPlaying = true
                    }
                }
            }
        }
    )
}
