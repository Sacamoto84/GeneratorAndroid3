package com.example.generator2;

import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.util.Log;

public class PlaybackEngine {

    static long mEngineHandle = 0;

    // Load native library
    static {
        System.loadLibrary("app");
    }

    static boolean create(Context context) {
        Log.d("!!!","---PlaybackEngine.create()---");
        if (mEngineHandle == 0) {
            setDefaultStreamValues(context);
            mEngineHandle = native_createEngine();
        }
        return (mEngineHandle != 0);
    }

    private static void setDefaultStreamValues(Context context) {
        Log.d("!!!","---PlaybackEngine.setDefaultStreamValues()---");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {

            AudioManager myAudioMgr = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            String sampleRateStr = myAudioMgr.getProperty(AudioManager.PROPERTY_OUTPUT_SAMPLE_RATE);
            int defaultSampleRate = Integer.parseInt(sampleRateStr);
            String framesPerBurstStr = myAudioMgr.getProperty(AudioManager.PROPERTY_OUTPUT_FRAMES_PER_BUFFER);
            int defaultFramesPerBurst = Integer.parseInt(framesPerBurstStr);

            native_setDefaultStreamValues(defaultSampleRate, defaultFramesPerBurst);

        }
    }

    static int start() {
        if (mEngineHandle != 0) {
            return native_startEngine(mEngineHandle);
        } else {
            return -1;
        }
    }

    static int stop() {
        if (mEngineHandle != 0) {
            return native_stopEngine(mEngineHandle);
        } else {
            return -1;
        }
    }

    static void delete() {
        if (mEngineHandle != 0) {
            native_deleteEngine(mEngineHandle);
        }
        mEngineHandle = 0;
    }

    static void setAudioApi(int audioApi) {
        if (mEngineHandle != 0) native_setAudioApi(mEngineHandle, audioApi);
    }

    static void setAudioDeviceId(int deviceId) {
        if (mEngineHandle != 0) native_setAudioDeviceId(mEngineHandle, deviceId);
    }

    static void setChannelCount(int channelCount) {
        if (mEngineHandle != 0) native_setChannelCount(mEngineHandle, channelCount);
    }

    static void setBufferSizeInBursts(int bufferSizeInBursts) {
        if (mEngineHandle != 0) native_setBufferSizeInBursts(mEngineHandle, bufferSizeInBursts);
    }

    static double getCurrentOutputLatencyMillis() {
        if (mEngineHandle == 0) return 0;
        return native_getCurrentOutputLatencyMillis(mEngineHandle);
    }

    static boolean isLatencyDetectionSupported() {
        return mEngineHandle != 0 && native_isLatencyDetectionSupported(mEngineHandle);
    }

    //Внешняя функция

    //Включалки
    static void CH_EN(int CH, boolean EN) {
        if (mEngineHandle != 0)
            native_setCH_EN(mEngineHandle, CH, EN);
    }

    static void CH_AM_EN(int CH, boolean EN) {
        if (mEngineHandle != 0)
            native_setCH_AMEN(mEngineHandle, CH, EN);
    }

    static void CH_FM_EN(int CH, boolean EN) {
        if (mEngineHandle != 0)
            native_setCH_FMEN(mEngineHandle, CH, EN);
    }

    //Установка частоты
    static void CH_Carrier_fr(int CH, float fr) {
        if (mEngineHandle != 0)
            native_setCH_Carrier_fr(mEngineHandle, CH, fr);
    }

    static void CH_AM_fr(int CH, float fr) {
        if (mEngineHandle != 0)
            native_setCH_AM_fr(mEngineHandle, CH, fr);
    }

    static void CH_FM_fr(int CH, float fr) {
        if (mEngineHandle != 0)
            native_setCH_FM_fr(mEngineHandle, CH, fr);
    }

    //Отправляем буффер 2048 байт
    static void CH_Send_Buffer(int CH, int mod, byte[] buf) {
        if (mEngineHandle != 0)
            native_setCH_Send_Buffer(mEngineHandle, CH, mod, buf);
    }

    static void CH_FM_Base(int CH, float fr) {
        if (mEngineHandle != 0)
            native_setCH_FM_Base(mEngineHandle, CH, fr);
    }

    static void CH_FM_Dev(int CH, float fr) {
        if (mEngineHandle != 0)
            native_setCH_FM_Dev(mEngineHandle, CH, fr);
    }

    // Native methods
    private static native long native_createEngine();

    private static native int native_startEngine(long engineHandle);

    private static native int native_stopEngine(long engineHandle);

    private static native void native_deleteEngine(long engineHandle);

    private static native void native_setAudioApi(long engineHandle, int audioApi);

    private static native void native_setAudioDeviceId(long engineHandle, int deviceId);

    private static native void native_setChannelCount(long mEngineHandle, int channelCount);

    private static native void native_setBufferSizeInBursts(long engineHandle, int bufferSizeInBursts);

    private static native double native_getCurrentOutputLatencyMillis(long engineHandle);

    private static native boolean native_isLatencyDetectionSupported(long engineHandle);

    private static native void native_setDefaultStreamValues(int sampleRate, int framesPerBurst);

    //Мои функции
    //Переключалка
    private static native void native_setCH_EN(long engineHandle, int CH, boolean EN);

    private static native void native_setCH_AMEN(long engineHandle, int CH, boolean EN);

    private static native void native_setCH_FMEN(long engineHandle, int CH, boolean EN);

    private static native void native_setCH_Carrier_fr(long engineHandle, int CH, float fr); //Изменить частоту несущей

    private static native void native_setCH_AM_fr(long engineHandle, int CH, float fr);    //Изменить частоту Амплитудной модуляции

    private static native void native_setCH_FM_fr(long engineHandle, int CH, float fr);    //Изменить частоту Частотной модуляции

    private static native void native_setCH_FM_Base(long engineHandle, int CH, float fr);    //Изменить базовую  частоту Частотной модуляции

    private static native void native_setCH_FM_Dev(long engineHandle, int CH, float fr);     //Изменить девиацию частоту Частотной модуляции

    private static native void native_setCH_Send_Buffer(long engineHandle, int CH, int mod, byte[] buf);
    
}
