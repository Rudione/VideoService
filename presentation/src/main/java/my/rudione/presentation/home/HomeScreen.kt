package my.rudione.presentation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.EntryPointAccessors
import my.rudione.di.ExoPlayerEntryPoint
import my.rudione.domain.model.Video
import my.rudione.presentation.components.HomeContentEmpty
import my.rudione.presentation.components.VideoListItem
import my.rudione.presentation.components.VideoPlayer

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val videoState by homeViewModel.state.collectAsState()
    val exoPlayer = EntryPointAccessors.fromApplication(LocalContext.current, ExoPlayerEntryPoint::class.java).exoplayer()

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
                        videoUrl = currentVideo.sources.firstOrNull() ?: "",
                        onNext = { homeViewModel.onEvent(VideoEvent.NextVideo) },
                        onPrevious = { homeViewModel.onEvent(VideoEvent.PreviousVideo) },
                        onClose = { homeViewModel.onEvent(VideoEvent.PauseVideo) },
                        exoPlayer = exoPlayer,
                        homeViewModel = homeViewModel
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
    if (videoList.isEmpty()) {
        HomeContentEmpty()
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp)
        ) {
            itemsIndexed(
                items = videoList,
                key = { index, video -> "${video.id}-$index" }
            ) { index, video ->

                val isFirstRow = index < 2
                val isLastRow = index >= videoList.size - (videoList.size % 2)

                val topPadding = if (isFirstRow) 16.dp else 8.dp
                val bottomPadding = if (isLastRow) 16.dp else 8.dp

                VideoListItem(
                    video = video,
                    onClick = { onVideoSelected(video) },
                    modifier = Modifier.padding(top = topPadding, bottom = bottomPadding)
                )
            }
        }
    }
}
