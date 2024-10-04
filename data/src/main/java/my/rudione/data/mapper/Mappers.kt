package my.rudione.data.mapper

import my.rudione.data.local.VideoEntity
import my.rudione.data.remote.dto.VideoDto
import my.rudione.domain.model.Video

fun VideoDto.toVideoEntity(): VideoEntity {
    return VideoEntity(
        id = 0,
        description = description,
        sources = sources,
        subtitle = subtitle,
        thumb = thumb,
        title = title
    )
}

fun VideoEntity.toVideoDto(): VideoEntity {
    return VideoEntity(
        id,
        description = description,
        sources = sources,
        subtitle = subtitle,
        thumb = thumb,
        title = title
    )
}

fun VideoEntity.toVideoDomain(): Video {
    return Video(
        id = id,
        description = description,
        sources = sources,
        subtitle = subtitle,
        thumb = thumb,
        title = title
    )
}