/*
 * Copyright 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#include <jni.h>
#include <oboe/Oboe.h>
#include "HelloOboeEngine.h"
#include "logging_macros.h"
#include "generator.h"

extern "C" {


JNIEXPORT jlong JNICALL
Java_com_example_generator2_PlaybackEngine_native_1createEngine(JNIEnv *env, jclass /*unused*/) {
    // We use std::nothrow so `new` returns a nullptr if the engine creation fails
    HelloOboeEngine *engine = new(std::nothrow) HelloOboeEngine();
    if (engine == nullptr) {
        LOGD("Could not instantiate HelloOboeEngine");
        return 0;
    }
    return reinterpret_cast<jlong>(engine);
}

JNIEXPORT jint JNICALL
Java_com_example_generator2_PlaybackEngine_native_1startEngine(
        JNIEnv *env,
        jclass,
        jlong engineHandle) {

    HelloOboeEngine *engine = reinterpret_cast<HelloOboeEngine *>(engineHandle);
    return static_cast<jint>(engine->start());
}

JNIEXPORT jint JNICALL
Java_com_example_generator2_PlaybackEngine_native_1stopEngine(
        JNIEnv *env,
        jclass,
        jlong engineHandle) {

    HelloOboeEngine *engine = reinterpret_cast<HelloOboeEngine *>(engineHandle);
    return static_cast<jint>(engine->stop());
}

JNIEXPORT void JNICALL
Java_com_example_generator2_PlaybackEngine_native_1deleteEngine(
        JNIEnv *env,
        jclass,
        jlong engineHandle) {

    HelloOboeEngine *engine = reinterpret_cast<HelloOboeEngine *>(engineHandle);
    engine->stop();
    delete engine;
}

JNIEXPORT void JNICALL
Java_com_example_generator2_PlaybackEngine_native_1setAudioApi(
        JNIEnv *env,
        jclass type,
        jlong engineHandle,
        jint audioApi) {

    HelloOboeEngine *engine = reinterpret_cast<HelloOboeEngine *>(engineHandle);
    if (engine == nullptr) {
        LOGE("Engine handle is invalid, call createHandle() to create a new one");
        return;
    }

    oboe::AudioApi api = static_cast<oboe::AudioApi>(audioApi);
    engine->setAudioApi(api);
}

JNIEXPORT void JNICALL
Java_com_example_generator2_PlaybackEngine_native_1setAudioDeviceId(
        JNIEnv *env,
        jclass,
        jlong engineHandle,
        jint deviceId) {

    HelloOboeEngine *engine = reinterpret_cast<HelloOboeEngine *>(engineHandle);
    if (engine == nullptr) {
        LOGE("Engine handle is invalid, call createHandle() to create a new one");
        return;
    }
    engine->setDeviceId(deviceId);
}

JNIEXPORT jint JNICALL
Java_com_example_generator2_PlaybackEngine_native_1getAudioDeviceId(
        JNIEnv *env,
        jclass,
        jlong engineHandle
)
{

    HelloOboeEngine *engine = reinterpret_cast<HelloOboeEngine *>(engineHandle);
    if (engine == nullptr) {
        LOGE("Engine handle is invalid, call createHandle() to create a new one");
        return -1;
    }

    return static_cast<jint>(engine->mStream->getDeviceId());
}

JNIEXPORT void JNICALL
Java_com_example_generator2_PlaybackEngine_native_1setChannelCount(
        JNIEnv *env,
        jclass type,
        jlong engineHandle,
        jint channelCount) {

    HelloOboeEngine *engine = reinterpret_cast<HelloOboeEngine *>(engineHandle);
    if (engine == nullptr) {
        LOGE("Engine handle is invalid, call createHandle() to create a new one");
        return;
    }
    engine->setChannelCount(channelCount);
}

JNIEXPORT void JNICALL
Java_com_example_generator2_PlaybackEngine_native_1setBufferSizeInBursts(
        JNIEnv *env,
        jclass,
        jlong engineHandle,
        jint bufferSizeInBursts) {

    HelloOboeEngine *engine = reinterpret_cast<HelloOboeEngine *>(engineHandle);
    if (engine == nullptr) {
        LOGE("Engine handle is invalid, call createHandle() to create a new one");
        return;
    }
    engine->setBufferSizeInBursts(bufferSizeInBursts);
}


JNIEXPORT jdouble JNICALL
Java_com_example_generator2_PlaybackEngine_native_1getCurrentOutputLatencyMillis(
        JNIEnv *env,
        jclass,
        jlong engineHandle) {

    HelloOboeEngine *engine = reinterpret_cast<HelloOboeEngine *>(engineHandle);
    if (engine == nullptr) {
        LOGE("Engine is null, you must call createEngine before calling this method");
        return static_cast<jdouble>(-1.0);
    }
    return static_cast<jdouble>(engine->getCurrentOutputLatencyMillis());
}

JNIEXPORT jboolean JNICALL
Java_com_example_generator2_PlaybackEngine_native_1isLatencyDetectionSupported(
        JNIEnv *env,
        jclass type,
        jlong engineHandle) {

    HelloOboeEngine *engine = reinterpret_cast<HelloOboeEngine *>(engineHandle);
    if (engine == nullptr) {
        LOGE("Engine is null, you must call createEngine before calling this method");
        return JNI_FALSE;
    }
    return (engine->isLatencyDetectionSupported() ? JNI_TRUE : JNI_FALSE);
}

JNIEXPORT void JNICALL
Java_com_example_generator2_PlaybackEngine_native_1setDefaultStreamValues(
        JNIEnv *env,
        jclass type,
        jint sampleRate,
        jint framesPerBurst) {
    oboe::DefaultStreamValues::SampleRate = (int32_t) sampleRate;
    oboe::DefaultStreamValues::FramesPerBurst = (int32_t) framesPerBurst;
}



//Мои функции



//CH EN
JNIEXPORT void JNICALL
Java_com_example_generator2_PlaybackEngine_native_1setCH_1EN(
        JNIEnv *env,
        jclass,
        jlong engineHandle,
        jint CH,
        jboolean EN) {

    HelloOboeEngine *engine = reinterpret_cast<HelloOboeEngine *>(engineHandle);
    if (engine == nullptr) {
        LOGE("Engine handle is invalid, call createHandle() to create a new one");
        return;
    }

    LOGI("JNI:setCH_1EN: CH:%d EN:%d", CH, EN);

    if (engine->mAudioSource) {
        if (CH)
            engine->mAudioSource->mGenerator->CH2.CH_EN = EN;
        else
            engine->mAudioSource->mGenerator->CH1.CH_EN = EN;
    }
}

JNIEXPORT void JNICALL
Java_com_example_generator2_PlaybackEngine_native_1setCH_1AMEN(
        JNIEnv *env,
        jclass,
        jlong engineHandle,
        jint CH,
        jboolean EN) {

    HelloOboeEngine *engine = reinterpret_cast<HelloOboeEngine *>(engineHandle);
    if (engine == nullptr) {
        LOGE("Engine handle is invalid, call createHandle() to create a new one");
        return;
    }

    LOGI("JNI:setCH_1AMEN: CH:%d EN:%d", CH, EN);

    if (engine->mAudioSource) {
        if (CH)
            engine->mAudioSource->mGenerator->CH2.AM_EN = EN;
        else
            engine->mAudioSource->mGenerator->CH1.AM_EN = EN;
    }
}

JNIEXPORT void JNICALL
Java_com_example_generator2_PlaybackEngine_native_1setCH_1FMEN(
        JNIEnv *env,
        jclass,
        jlong engineHandle,
        jint CH,
        jboolean EN) {

    HelloOboeEngine *engine = reinterpret_cast<HelloOboeEngine *>(engineHandle);
    if (engine == nullptr) {
        LOGE("Engine handle is invalid, call createHandle() to create a new one");
        return;
    }

    LOGI("JNI:setCH_1FMEN: CH:%d EN:%d", CH, EN);

    if (engine->mAudioSource) {
        if (CH)
            engine->mAudioSource->mGenerator->CH2.FM_EN = EN;
        else
            engine->mAudioSource->mGenerator->CH1.FM_EN = EN;
    }
}

//Изменить частоту несущей
JNIEXPORT void JNICALL
Java_com_example_generator2_PlaybackEngine_native_1setCH_1Carrier_1fr(
        JNIEnv *env,
        jclass,
        jlong engineHandle,
        jint CH,
        jfloat fr
) {
    HelloOboeEngine *engine = reinterpret_cast<HelloOboeEngine *>(engineHandle);
    if (engine == nullptr) {
        LOGE("Engine handle is invalid, call createHandle() to create a new one");
        return;
    }

    LOGI("JNI:setCH_Carrier_fr: CH:%d fr%f", CH, fr);

    if (engine->mAudioSource) {
        if (CH == 0)
            engine->mAudioSource->mGenerator->CH1.Carrier_fr = fr;
        else
            engine->mAudioSource->mGenerator->CH2.Carrier_fr = fr;
    }
}

//Изменить частоту AM
JNIEXPORT void JNICALL
Java_com_example_generator2_PlaybackEngine_native_1setCH_1AM_1fr(
        JNIEnv *env,
        jclass,
        jlong engineHandle,
        jint CH,
        jfloat fr
) {
    HelloOboeEngine *engine = reinterpret_cast<HelloOboeEngine *>(engineHandle);
    if (engine == nullptr) {
        LOGE("Engine handle is invalid, call createHandle() to create a new one");
        return;
    }

    LOGI("JNI:setCH_AM_fr: CH:%d FR%f", CH, fr);

    if (engine->mAudioSource) {
        if (CH == 0)
            engine->mAudioSource->mGenerator->CH1.AM_fr = fr;
        else
            engine->mAudioSource->mGenerator->CH2.AM_fr = fr;
    }
}

//Изменить частоту FM
JNIEXPORT void JNICALL
Java_com_example_generator2_PlaybackEngine_native_1setCH_1FM_1fr(
        JNIEnv *env,
        jclass,
        jlong engineHandle,
        jint CH,
        jfloat fr
) {
    HelloOboeEngine *engine = reinterpret_cast<HelloOboeEngine *>(engineHandle);
    if (engine == nullptr) {
        LOGE("Engine handle is invalid, call createHandle() to create a new one");
        return;
    }

    LOGI("JNI:setCH_FM_fr: CH:%d FR%f", CH, fr);

    if (engine->mAudioSource) {
        if (CH == 0)
            engine->mAudioSource->mGenerator->CH1.FM_mod_fr = fr;
        else
            engine->mAudioSource->mGenerator->CH2.FM_mod_fr = fr;
    }
}


extern "C"
JNIEXPORT void JNICALL
Java_com_example_generator2_PlaybackEngine_native_1setCH_1Send_1Buffer(JNIEnv *env,
                                                                       jclass clazz,
                                                                       jlong engineHandle,
                                                                       jint CH, jint mod,
                                                                       jbyteArray buf) {


    HelloOboeEngine *engine = reinterpret_cast<HelloOboeEngine *>(engineHandle);
    if (engine == nullptr) {
        LOGE("Engine handle is invalid, call createHandle() to create a new one");
        return;
    }

    LOGI("JNI:setCH_Send_Buffer: CH:%d mod%d", CH, mod);

    jbyte *arrayBody = env->GetByteArrayElements(buf, 0);
    jsize theArrayLengthJ = env->GetArrayLength(buf);

    uint8_t *starter = (uint8_t *) arrayBody;

    starter++;
    int i = (int) theArrayLengthJ;
    if (i != 2048) {
        LOGI("!ERROR JNI:Send: buf != 2048");
        return;
    }
    uint8_t inBuf[2048];

    for (i = 0; i < 2048; i++)
        inBuf[i] = *starter++;

    uint16_t inBuf16[1024];
    for (i = 0; i < 1024; i++)
        inBuf16[i] = (inBuf[(i * 2)] << 8) | inBuf[i * 2 + 1];

    if (CH == 0) {
        if (mod == 0) {
            for (i = 0; i < 1024; i++)
                engine->mAudioSource->mGenerator->CH1.buffer_carrier[i] = inBuf16[i];
        }

        if (mod == 1)  //AM
        {
            for (i = 0; i < 1024; i++)
                engine->mAudioSource->mGenerator->CH1.buffer_am[i] = inBuf16[i];
        }

        if (mod == 2) //FM
        {
            //Помещаем в буффер исходника FM модуляции
            for (i = 0; i < 1024; i++)
                engine->mAudioSource->mGenerator->CH1.source_buffer_fm[i] = inBuf16[i];

            engine->mAudioSource->mGenerator->CreateFM_CH1();

        }
    } else {
        if (mod == 0) {
            for (i = 0; i < 1024; i++)
                engine->mAudioSource->mGenerator->CH2.buffer_carrier[i] =
                        (inBuf[(i * 2)] << 8) | inBuf[i * 2 + 1];
        }

        if (mod == 1) {
            for (i = 0; i < 1024; i++)
                engine->mAudioSource->mGenerator->CH2.buffer_am[i] =
                        (inBuf[(i * 2)] << 8) | inBuf[i * 2 + 1];
        }

        if (mod == 2) //FM
        {
            //Помещаем в буффер исходника FM модуляции
            for (i = 0; i < 1024; i++)
                engine->mAudioSource->mGenerator->CH2.source_buffer_fm[i] = inBuf16[i];

            engine->mAudioSource->mGenerator->CreateFM_CH2();
        }
    }
}


JNIEXPORT void JNICALL
Java_com_example_generator2_PlaybackEngine_native_1setCH_1FM_1Base(JNIEnv *env,
                                                                   jclass clazz,
                                                                   jlong engineHandle,
                                                                   jint CH, float fr) {


    HelloOboeEngine *engine = reinterpret_cast<HelloOboeEngine *>(engineHandle);
    if (engine == nullptr) {
        LOGE("Engine handle is invalid, call createHandle() to create a new one");
        return;
    }

    LOGI("JNI:setCH_FM_Base: CH:%d FR%f", CH, fr);

    if (engine->mAudioSource) {
        if (CH == 0) {
            engine->mAudioSource->mGenerator->CH1.FM_Base = fr;
            engine->mAudioSource->mGenerator->CreateFM_CH1();
        } else {
            engine->mAudioSource->mGenerator->CH2.FM_Base = fr;
            engine->mAudioSource->mGenerator->CreateFM_CH2();
        }
    }


}

JNIEXPORT void JNICALL
Java_com_example_generator2_PlaybackEngine_native_1setCH_1FM_1Dev(JNIEnv *env,
                                                                  jclass clazz,
                                                                  jlong engineHandle,
                                                                  jint CH, jfloat fr) {


    HelloOboeEngine *engine = reinterpret_cast<HelloOboeEngine *>(engineHandle);
    if (engine == nullptr) {
        LOGE("Engine handle is invalid, call createHandle() to create a new one");
        return;
    }

    LOGI("JNI:setCH_FM_Dev: CH:%d FR%f", CH, fr);

    if (CH == 0) {
        engine->mAudioSource->mGenerator->CH1.FM_Dev = fr;
        engine->mAudioSource->mGenerator->CreateFM_CH1();
    } else {
        engine->mAudioSource->mGenerator->CH2.FM_Dev = fr;
        engine->mAudioSource->mGenerator->CreateFM_CH2();
    }

}


}




