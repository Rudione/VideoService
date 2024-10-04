package my.rudione.presentation.home

import my.rudione.domain.common.Event
import my.rudione.domain.model.Video

sealed class VideoEvent: Event {

    data class LoadVideos(val forceFetchFromRemote: Boolean): VideoEvent()

    data class PlayVideo(val video: Video): VideoEvent()

    object PauseVideo: VideoEvent()

    object PreviousVideo: VideoEvent()

    object NextVideo: VideoEvent()

    data class SeekTo(val progress: Float): VideoEvent()

}