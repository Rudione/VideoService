package my.rudione.presentation.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import my.rudione.domain.VideoRepository
import my.rudione.domain.common.Resource
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val videoRepository: VideoRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(VideoState())
    val state: StateFlow<VideoState> = _state.asStateFlow()

    init {
        onEvent(VideoEvent.LoadVideosFirstTime(false))
    }

    fun onEvent(event: VideoEvent) {
        when (event) {
            is VideoEvent.LoadVideos -> getLoadVideos(true)
            is VideoEvent.LoadVideosFirstTime -> getLoadVideos(false)
            is VideoEvent.PlayVideo -> {
                _state.update {
                    it.copy(currentVideo = event.video, isPlaying = true, videoProgress = 0f)
                }
            }
            is VideoEvent.PauseVideo -> {
                _state.update {
                    it.copy(isPlaying = false)
                }
            }
            is VideoEvent.NextVideo -> {
                val currentIndex = _state.value.videoList.indexOf(_state.value.currentVideo)
                val nextIndex = if (currentIndex != -1) (currentIndex + 1) % _state.value.videoList.size else 0
                _state.update {
                    it.copy(
                        currentVideo = _state.value.videoList[nextIndex],
                        isPlaying = true,
                        videoProgress = 0f
                    )
                }
            }
            is VideoEvent.PreviousVideo -> {
                val currentIndex = _state.value.videoList.indexOf(_state.value.currentVideo)
                val prevIndex = if (currentIndex > 0) currentIndex - 1 else _state.value.videoList.size - 1
                _state.update {
                    it.copy(
                        currentVideo = _state.value.videoList[prevIndex],
                        isPlaying = true,
                        videoProgress = 0f
                    )
                }
            }
            is VideoEvent.SeekTo -> {
                _state.update {
                    it.copy(videoProgress = event.progress)
                }
            }
        }
    }

    private fun getLoadVideos(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            videoRepository.getAllVideos(forceFetchFromRemote).collectLatest { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.let { videoList ->
                            _state.update {
                                it.copy(
                                    videoList = videoList,
                                    currentVideo = videoList.first(),
                                    isLoading = false
                                )
                            }
                        }
                    }
                    is Resource.Error -> {
                        _state.update { it.copy(isLoading = false) }
                    }
                    is Resource.Loading -> {
                        _state.update {
                            it.copy(isLoading = resource.isLoading)
                        }
                    }
                }
            }
        }
    }
}

