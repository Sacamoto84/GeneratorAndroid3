package com.example.generator2.di

import android.content.Context
import com.example.generator2.PlaybackEngine
import com.example.generator2.audio_device.AudioDevice
import com.example.generator2.backup.Backup
import com.example.generator2.screens.firebase.Firebas
import com.example.generator2.screens.scripting.ui.ScriptKeyboard
import com.example.generator2.util.UtilsKT
import com.example.generator2.vm.Hub
import com.example.generator2.vm.Script
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
    fun provideScript(): Script {
        return Script()
    }

    @Provides
    @Singleton
    fun provideKeyboard(script: Script): ScriptKeyboard {
        return ScriptKeyboard(script)
    }

    @Provides
    @Singleton
    fun providePlaybackEngine(@ApplicationContext context: Context): PlaybackEngine {
        return PlaybackEngine(context)
    }

    @Provides
    @Singleton
    fun provideAudioDevice(
        @ApplicationContext context: Context,
        playbackEngine: PlaybackEngine,
        script: Script,
        utils: UtilsKT,
    ): AudioDevice {
        return AudioDevice(context, playbackEngine, script, utils)
    }

    @Provides
    @Singleton
    fun provideBackup(@ApplicationContext context: Context): Backup {
        return Backup(context)
    }

    @Provides
    @Singleton
    fun provideFirebase(@ApplicationContext context: Context): Firebas {
        return Firebas(context)
    }

    @Provides
    @Singleton
    fun provideHub(
        utils: UtilsKT,
        script: Script,
        keyboard: ScriptKeyboard,
        playbackEngine: PlaybackEngine,
        audioDevice: AudioDevice,
        firebase: Firebas,
        backup: Backup
    ): Hub {
        return Hub(
            utils = utils,
            script = script,
            keyboard = keyboard,
            playbackEngine = playbackEngine,
            audioDevice = audioDevice,
            firebase = firebase,
            backup = backup
        )
    }


}