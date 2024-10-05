package my.rudione.presentation.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import my.rudione.presentation.home.HomeViewModel
import my.rudione.presentation.home.VideoEvent

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier,
    videoUrl: String,
    onClose: () -> Unit,
    exoPlayer: ExoPlayer,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    var isPlaying by rememberSaveable { mutableStateOf(true) }
    val currentVideoUrl by rememberSaveable { mutableStateOf(videoUrl) }
    val allMediaItems = homeViewModel.state.value.videoList.map { video ->
        MediaItem.fromUri(Uri.parse(video.sources.firstOrNull() ?: ""))
    }

    DisposableEffect(currentVideoUrl) {
        exoPlayer.setMediaItems(allMediaItems)
        exoPlayer.prepare()
        val mediaItemIndex =
            allMediaItems.indexOfFirst { it.localConfiguration?.uri.toString() == currentVideoUrl }
        if (mediaItemIndex != -1) {
            exoPlayer.seekTo(mediaItemIndex, homeViewModel.state.value.videoProgress.toLong())
            exoPlayer.play()
        }

        onDispose {
            exoPlayer.stop()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.8f))
            .padding(horizontal = if (isLandscape) 32.dp else 0.dp)
            .padding(vertical = if (isPortrait) 24.dp else 0.dp)
    ) {
        AndroidView(
            factory = {
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = true
                }
            },
            update = {
                it.player = exoPlayer
                it.useController = true
            },
            modifier = Modifier.fillMaxSize()
        )

        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 24.dp)
                .padding(end = 32.dp)
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            IconButton(onClick = onClose, modifier = Modifier.size(64.dp)) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.White
                )
            }
        }
    }

    DisposableEffect(Unit) {
        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(isPlayingNow: Boolean) {
                isPlaying = isPlayingNow
            }

            override fun onPlayerError(error: PlaybackException) {
            }

            override fun onEvents(player: Player, events: Player.Events) {
                super.onEvents(player, events)
                homeViewModel.onEvent(VideoEvent.SeekTo(player.currentPosition.toFloat()))
            }
        }

        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
        }
    }
}