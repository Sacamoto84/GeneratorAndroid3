package com.example.generator2.di

import android.content.Context
import com.example.generator2.Observe
import com.example.generator2.PlaybackEngine
import com.example.generator2.UtilsKT
import com.example.generator2.scripting.Script
import com.example.generator2.scripting.ui.ScriptKeyboard
import com.example.generator2.vmLiveData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HomeActivityModule {

    @Provides
    @Singleton
    fun provideUtilsKT(
        @ApplicationContext context: Context, playbackEngine: PlaybackEngine
    ): UtilsKT {
        return UtilsKT(context, playbackEngine)
    }

    @Provides
    @Singleton
    fun provideLiveData(): vmLiveData {
        return vmLiveData()
    }

    @Provides
    @Singleton
    fun provideScript(liveData: vmLiveData): Script {
        return Script(liveData)
    }


    @Provides
    @Singleton
    fun provideKeyboard(script: Script): ScriptKeyboard {
        return ScriptKeyboard(script)
    }

    @Provides
    @Singleton
    fun provideObserver(
        liveData: vmLiveData,
        playbackEngine: PlaybackEngine,
        utils: UtilsKT
    ): Observe {
        return Observe(liveData, playbackEngine, utils)
    }

    @Provides
    @Singleton
    fun providePlaybackEngine(): PlaybackEngine {
        return PlaybackEngine()
    }

}