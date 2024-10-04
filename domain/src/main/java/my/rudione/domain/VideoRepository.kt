package my.rudione.domain

import my.rudione.domain.model.Video

interface VideoRepository {
    fun getAllVideos(): List<Video>
}