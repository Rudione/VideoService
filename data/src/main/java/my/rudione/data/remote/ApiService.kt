package my.rudione.data.remote

import my.rudione.data.remote.dto.VideoDto
import retrofit2.http.GET

interface ApiService {
    @GET("326609823a8fde2b7d308318944f5537/raw/6137bf2c673e51787bc387723bbeff2e83349a56/video_json.json")
    suspend fun getAllVideo(): List<VideoDto>
}