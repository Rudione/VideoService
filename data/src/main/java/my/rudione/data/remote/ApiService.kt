package my.rudione.data.remote

import my.rudione.data.remote.dto.MediaResponse
import retrofit2.http.GET

interface ApiService {
    @GET("jsturgis/3b19447b304616f18657/raw/gistfile1.txt")
    suspend fun getAllVideo(): MediaResponse
}