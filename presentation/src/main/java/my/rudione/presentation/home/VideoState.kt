package my.rudione.presentation.home

import my.rudione.domain.model.Video

data class VideoState(
    val videoList: List<Video> = emptyList(),
    val currentVideo: Video? = null,
    val isPlaying: Boolean = false,
    val isLoading: Boolean = false,
    val videoProgress: Float = 0f
)