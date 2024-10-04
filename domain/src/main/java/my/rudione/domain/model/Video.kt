package my.rudione.domain.model

data class Video(
    val id: Int,
    val description: String,
    val sources: List<String>,
    val subtitle: String,
    val thumb: String,
    val title: String
) {
    val fullThumbUrl: String
        get() = "https://storage.googleapis.com/gtv-videos-bucket/sample/"
}