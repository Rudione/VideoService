package my.rudione.videoservice.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import my.rudione.data.repository.VideoRepositoryImpl
import my.rudione.domain.VideoRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesVideoRepository(
        videoRepositoryImpl: VideoRepositoryImpl
    ): VideoRepository {
        return videoRepositoryImpl
    }
}