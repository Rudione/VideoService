package my.rudione.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import my.rudione.domain.VideoRepository
import my.rudione.domain.common.Resource
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val videoRepository: VideoRepository
) : ViewModel() {

    private val _state = MutableStateFlow(VideoState())
    val state: StateFlow<VideoState> = _state.asStateFlow()

    init {
        onEvent(VideoEvent.LoadVideos)
    }

    fun onEvent(event: VideoEvent) {
        when (event) {
            is VideoEvent.LoadVideos -> getLoadVideos()
            is VideoEvent.PlayVideo -> {
                _state.update {
                    it.copy(currentVideo = event.video, isPlaying = true)
                }
            }
            is VideoEvent.PauseVideo -> {
                _state.update {
                    it.copy(isPlaying = false)
                }
            }
            is VideoEvent.NextVideo -> {
                val currentIndex = _state.value.videoList.indexOf(_state.value.currentVideo)
                val nextIndex = (currentIndex + 1).coerceAtMost(_state.value.videoList.size - 1)
                _state.update {
                    it.copy(currentVideo = _state.value.videoList[nextIndex], isPlaying = true)
                }
            }
            is VideoEvent.PreviousVideo -> {
                val currentIndex = _state.value.videoList.indexOf(_state.value.currentVideo)
                val prevIndex = (currentIndex - 1).coerceAtLeast(0)
                _state.update {
                    it.copy(currentVideo = _state.value.videoList[prevIndex], isPlaying = true)
                }
            }
            is VideoEvent.SeekTo -> {
                _state.update {
                    it.copy(videoProgress = event.progress)
                }
            }
        }
    }

    private fun getLoadVideos() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            videoRepository.getAllVideos(forceFetchFromRemote = false).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                videoList = resource.data ?: emptyList(),
                                currentVideo = resource.data?.firstOrNull(),
                                isLoading = false
                            )
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
