package my.rudione.presentation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import my.rudione.domain.model.Video
import my.rudione.presentation.components.VideoListItem
import my.rudione.presentation.components.VideoPlayer

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val videoState by homeViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        homeViewModel.onEvent(VideoEvent.LoadVideos)
    }

    Scaffold(
        content = {
            VideoPlaylist(
                videoList = videoState.videoList,
                onVideoSelected = { video ->
                    homeViewModel.onEvent(VideoEvent.PlayVideo(video))
                }
            )

            videoState.currentVideo?.let { currentVideo ->
                if (videoState.isPlaying) {
                    VideoPlayer(
                        videoUrl = currentVideo.sources.first(),
                        onClose = {
                            homeViewModel.onEvent(VideoEvent.PauseVideo)
                        }
                    )
                }
            }
        }
    )
}

@Composable
fun VideoPlaylist(
    videoList: List<Video>,
    onVideoSelected: (Video) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp)
    ) {
        itemsIndexed(videoList, key = { index, movie -> movie.id }) { index, movie ->
            VideoListItem(video = movie, onClick = {
                onVideoSelected(movie)
            })
        }
    }
}