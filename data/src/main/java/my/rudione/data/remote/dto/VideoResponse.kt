package my.rudione.data.remote.dto

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @SerializedName("categories") val categories: List<VideoCategory>
)