package my.rudione.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import my.rudione.data.local.VideoDatabase
import my.rudione.data.mapper.toVideoDomain
import my.rudione.data.mapper.toVideoEntity
import my.rudione.data.remote.ApiService
import my.rudione.domain.VideoRepository
import my.rudione.domain.common.Resource
import my.rudione.domain.model.Video
import retrofit2.HttpException
import java.io.IOException

class VideoRepositoryImpl(
    private val videoApi: ApiService,
    private val videoDatabase: VideoDatabase
): VideoRepository {
    override fun getAllVideos(
        forceFetchFromRemote: Boolean
    ): Flow<Resource<List<Video>>> {
        return flow {
            emit(Resource.Loading(true))

            val localVideoList = videoDatabase.videoDao().getAllVideos()

            val shouldLoadLocalVideo = localVideoList.isNotEmpty() && !forceFetchFromRemote

            if(shouldLoadLocalVideo) {
                emit(Resource.Success(
                    data = localVideoList.map { videoEntity ->
                        videoEntity.toVideoDomain()
                    }
                ))

                emit(Resource.Loading(false))
                return@flow
            }

            val videoListFromApi = try {
                videoApi.getAllVideo()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Помилка завантаження відео"))
                emit(Resource.Loading(false))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Помилка завантаження відео"))
                emit(Resource.Loading(false))
                return@flow
            }

            val videoEntities = videoListFromApi.map { videoDto ->
                videoDto.toVideoEntity()
            }

            videoDatabase.videoDao().insertAll(videoEntities)

            emit(Resource.Success(
                data = videoEntities.map { it.toVideoDomain() }
            ))
            emit(Resource.Loading(false))
        }
    }

    override fun getVideosByTitle(query: String): Flow<Resource<List<Video>>> {
        return flow {
            emit(Resource.Loading(true))

            val videoEntity = videoDatabase.videoDao().getVideosByTitle(query)

            if(videoEntity != null) {
                emit(Resource.Success(videoEntity.map { it.toVideoDomain() }))
                emit(Resource.Loading(false))
                return@flow
            }

            emit(Resource.Error(message = "Відео не знайдено"))
            emit(Resource.Loading(false))
        }
    }


}