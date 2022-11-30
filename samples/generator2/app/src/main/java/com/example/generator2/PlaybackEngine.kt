package com.example.generator2

import android.content.Context
import android.media.AudioManager
import android.util.Log

class PlaybackEngine {

    private var mEngineHandle: Long = 0

    init {
        System.loadLibrary("app")
    }


    fun create(context: Context): Boolean {
        Log.d("!!!", "---PlaybackEngine.create()---")
        if (mEngineHandle == 0L) {
            setDefaultStreamValues(context)
            mEngineHandle = native_createEngine()
        }
        return mEngineHandle != 0L
    }

    private fun setDefaultStreamValues(context: Context) {

        Log.d("!!!", "---PlaybackEngine.setDefaultStreamValues()---")
        val myAudioMgr = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val sampleRateStr = myAudioMgr.getProperty(AudioManager.PROPERTY_OUTPUT_SAMPLE_RATE)
        val defaultSampleRate = sampleRateStr.toInt()
        val framesPerBurstStr =
            myAudioMgr.getProperty(AudioManager.PROPERTY_OUTPUT_FRAMES_PER_BUFFER)
        val defaultFramesPerBurst = framesPerBurstStr.toInt()
        native_setDefaultStreamValues(defaultSampleRate, defaultFramesPerBurst)

    }

    fun start(): Int {
        return if (mEngineHandle != 0L) {
            native_startEngine(mEngineHandle)
        } else {
            -1
        }
    }

    fun stop(): Int {
        return if (mEngineHandle != 0L) {
            native_stopEngine(mEngineHandle)
        } else {
            -1
        }
    }

    fun delete() {
        if (mEngineHandle != 0L) {
            native_deleteEngine(mEngineHandle)
        }
        mEngineHandle = 0
    }

    fun setAudioApi(audioApi: Int) {
        if (mEngineHandle != 0L) native_setAudioApi(
            mEngineHandle,
            audioApi
        )
    }

    fun setAudioDeviceId(deviceId: Int) {
        if (mEngineHandle != 0L) native_setAudioDeviceId(
            mEngineHandle,
            deviceId
        )
    }

    fun setChannelCount(channelCount: Int) {
        if (mEngineHandle != 0L) native_setChannelCount(
            mEngineHandle,
            channelCount
        )
    }

    fun setBufferSizeInBursts(bufferSizeInBursts: Int) {
        if (mEngineHandle != 0L) native_setBufferSizeInBursts(
            mEngineHandle, bufferSizeInBursts
        )
    }

    fun getCurrentOutputLatencyMillis(): Double {
        return if (mEngineHandle == 0L) 0.0 else native_getCurrentOutputLatencyMillis(
            mEngineHandle
        )
    }

    fun isLatencyDetectionSupported(): Boolean {
        return mEngineHandle != 0L && native_isLatencyDetectionSupported(
            mEngineHandle
        )
    }

    //Внешняя функция
    //Включалки
    fun CH_EN(CH: Int, EN: Boolean) {
        if (mEngineHandle != 0L) native_setCH_EN(
            mEngineHandle,
            CH,
            EN
        )
    }

    fun CH_AM_EN(CH: Int, EN: Boolean) {
        if (mEngineHandle != 0L) native_setCH_AMEN(
            mEngineHandle,
            CH,
            EN
        )
    }

    fun CH_FM_EN(CH: Int, EN: Boolean) {
        if (mEngineHandle != 0L) native_setCH_FMEN(
            mEngineHandle,
            CH,
            EN
        )
    }

    //Установка частоты
    fun CH_Carrier_fr(CH: Int, fr: Float) {
        if (mEngineHandle != 0L) native_setCH_Carrier_fr(
            mEngineHandle,
            CH,
            fr
        )
    }

    fun CH_AM_fr(CH: Int, fr: Float) {
        if (mEngineHandle != 0L) native_setCH_AM_fr(
            mEngineHandle,
            CH,
            fr
        )
    }

    fun CH_FM_fr(CH: Int, fr: Float) {
        if (mEngineHandle != 0L) native_setCH_FM_fr(
            mEngineHandle,
            CH,
            fr
        )
    }

    //Отправляем буффер 2048 байт
    fun CH_Send_Buffer(CH: Int, mod: Int, buf: ByteArray?) {
        if (mEngineHandle != 0L) native_setCH_Send_Buffer(
            mEngineHandle,
            CH,
            mod,
            buf!!
        )
    }

    fun CH_FM_Base(CH: Int, fr: Float) {
        if (mEngineHandle != 0L) native_setCH_FM_Base(
            mEngineHandle,
            CH,
            fr
        )
    }

    fun CH_FM_Dev(CH: Int, fr: Float) {
        if (mEngineHandle != 0L) native_setCH_FM_Dev(
            mEngineHandle,
            CH,
            fr
        )
    }

    // Native methods
    private external fun native_createEngine(): Long

    private external fun native_startEngine(engineHandle: Long): Int

    private external fun native_stopEngine(engineHandle: Long): Int

    private external fun native_deleteEngine(engineHandle: Long)

    private external fun native_setAudioApi(engineHandle: Long, audioApi: Int)

    private external fun native_setAudioDeviceId(engineHandle: Long, deviceId: Int)

    private external fun native_setChannelCount(mEngineHandle: Long, channelCount: Int)

    private external fun native_setBufferSizeInBursts(engineHandle: Long, bufferSizeInBursts: Int)

    private external fun native_getCurrentOutputLatencyMillis(engineHandle: Long): Double

    private external fun native_isLatencyDetectionSupported(engineHandle: Long): Boolean

    private external fun native_setDefaultStreamValues(sampleRate: Int, framesPerBurst: Int)

    //Мои функции
    //Переключалка
    private external fun native_setCH_EN(engineHandle: Long, CH: Int, EN: Boolean)

    private external fun native_setCH_AMEN(engineHandle: Long, CH: Int, EN: Boolean)

    private external fun native_setCH_FMEN(engineHandle: Long, CH: Int, EN: Boolean)

    private external fun native_setCH_Carrier_fr(
        engineHandle: Long, CH: Int, fr: Float
    ) //Изменить частоту несущей


    private external fun native_setCH_AM_fr(
        engineHandle: Long, CH: Int, fr: Float
    ) //Изменить частоту Амплитудной модуляции


    private external fun native_setCH_FM_fr(
        engineHandle: Long, CH: Int, fr: Float
    ) //Изменить частоту Частотной модуляции


    private external fun native_setCH_FM_Base(
        engineHandle: Long, CH: Int, fr: Float
    ) //Изменить базовую  частоту Частотной модуляции


    private external fun native_setCH_FM_Dev(
        engineHandle: Long, CH: Int, fr: Float
    ) //Изменить девиацию частоту Частотной модуляции


    private external fun native_setCH_Send_Buffer(
        engineHandle: Long, CH: Int, mod: Int, buf: ByteArray
    )

}
