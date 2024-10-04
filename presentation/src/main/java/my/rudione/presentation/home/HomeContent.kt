package my.rudione.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import my.rudione.domain.model.Video
import my.rudione.presentation.components.HomeContentEmpty
import my.rudione.presentation.components.VideoListItem

@Composable
fun HomeContent(
    videoState: VideoState,
    onEvent: (VideoEvent) -> Unit,
    isVideoPlaying: Boolean,
    currentVideoSource: String,
    onVideoClick: (Video) -> Unit  // Додаємо колбек для кліку
) {
    if (videoState.videoList.isEmpty()) {
        HomeContentEmpty()
    } else {
        VideoPlaylist(
            videoState = videoState,
            onEvent = onEvent,
            isVideoPlaying = isVideoPlaying,
            currentVideoSource = currentVideoSource,
            onVideoClick = onVideoClick  // Передаємо колбек
        )
    }
}

@Composable
fun VideoPlaylist(
    videoState: VideoState,
    onEvent: (VideoEvent) -> Unit,
    isVideoPlaying: Boolean,
    currentVideoSource: String,
    onVideoClick: (Video) -> Unit  // Додаємо колбек для кліку
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp)
    ) {
        itemsIndexed(videoState.videoList, key = { index, movie -> movie.id }) { index, movie ->
            VideoListItem(video = movie, onClick = {
                onVideoClick(movie)
            })

            if (index >= videoState.videoList.size - 1 && !videoState.isLoading) {
                onEvent(VideoEvent.LoadVideos)
            }
        }
    }
}
