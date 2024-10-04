package my.rudione.domain

import my.rudione.domain.model.Video
import my.rudione.domain.common.Resource
import kotlinx.coroutines.flow.Flow

interface VideoRepository {
    fun getAllVideos(forceFetchFromRemote: Boolean): Flow<Resource<List<Video>>>

    fun getVideosByTitle(query: String): Flow<Resource<List<Video>>>
}