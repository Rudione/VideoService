package my.rudione.data.remote.dto

import com.google.gson.annotations.SerializedName

data class VideoDto(
    @SerializedName("description") val description: String,
    @SerializedName("sources") val sources: List<String>,
    @SerializedName("subtitle") val subtitle: String,
    @SerializedName("thumb") val thumb: String,
    @SerializedName("title") val title: String
)
