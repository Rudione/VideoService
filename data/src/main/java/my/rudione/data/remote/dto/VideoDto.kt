package my.rudione.data.remote.dto

data class VideoDto(
    val description: String,
    val sources: List<String>,
    val subtitle: String,
    val thumb: String,
    val title: String
)