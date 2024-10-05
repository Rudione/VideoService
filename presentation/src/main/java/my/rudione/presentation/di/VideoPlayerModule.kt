package my.rudione.di

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ExoPlayerModule {

    @Provides
    fun provideExoPlayer(context: Context): ExoPlayer {
        return ExoPlayer.Builder(context).build()
    }

    @Provides
    fun provideContext(application: android.app.Application): Context {
        return application.applicationContext
    }
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface ExoPlayerEntryPoint {
    fun exoplayer(): ExoPlayer
}