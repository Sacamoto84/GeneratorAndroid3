package com.example.generator2.di

import android.content.Context
import com.example.generator2.PlaybackEngine
import com.example.generator2.UtilsKT
import com.example.generator2.audio_device.AudioDevice
import com.example.generator2.backup.Backup
import com.example.generator2.screens.scripting.ui.ScriptKeyboard
import com.example.generator2.vm.Script
import com.example.generator2.vm.vmLiveData
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
    fun providePlaybackEngine( @ApplicationContext context: Context,): PlaybackEngine {
        return PlaybackEngine(context)
    }

    @Provides
    @Singleton
    fun provideAudioDevice( @ApplicationContext context: Context, playbackEngine: PlaybackEngine, script : Script): AudioDevice {
        return AudioDevice(context, playbackEngine, script)
    }

    @Provides
    @Singleton
    fun provideBackup( @ApplicationContext context: Context): Backup {
        return Backup(context)
    }

}