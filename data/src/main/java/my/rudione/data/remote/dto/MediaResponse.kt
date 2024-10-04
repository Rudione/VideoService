package my.rudione.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MediaResponse(
    @SerializedName("categories") val categories: List<MediaCategory>
)