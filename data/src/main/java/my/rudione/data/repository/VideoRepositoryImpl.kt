package my.rudione.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import my.rudione.data.remote.ApiService
import my.rudione.domain.VideoRepository
import my.rudione.domain.common.Resource
import my.rudione.domain.model.Video

class VideoRepositoryImpl(
    private val videoApi: ApiService
): VideoRepository {
    override fun getAllVideos(
        forceFetchFromRemote: Boolean
    ): Flow<Resource<List<Video>>> {
        return flow {
            emit(Resource.Loading(true))


        }
    }

}