package my.rudione.presentation.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import my.rudione.presentation.home.HomeViewModel
import my.rudione.presentation.home.VideoEvent

@Composable
fun VideoPlayer(
    videoUrl: String,
    onClose: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    exoPlayer: ExoPlayer,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var isPlaying by remember { mutableStateOf(true) }

    DisposableEffect(videoUrl) {
        val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()

        exoPlayer.seekTo(homeViewModel.state.value.videoProgress.toLong())
        exoPlayer.play()

        onDispose {
            homeViewModel.onEvent(VideoEvent.SeekTo(exoPlayer.currentPosition.toFloat()))
            exoPlayer.stop()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.8f))
            .padding(vertical = 24.dp)
    ) {
        AndroidView(
            factory = {
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = true
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 96.dp)
                .padding(horizontal = 16.dp)
        ) {
            IconButton(onClick = onPrevious, modifier = Modifier.size(64.dp)) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Previous",
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            IconButton(onClick = onNext, modifier = Modifier.size(64.dp)) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Next",
                    tint = Color.White
                )
            }
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
        }

        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
        }
    }
}
