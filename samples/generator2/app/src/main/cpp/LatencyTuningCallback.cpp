/**
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

#include <time.h>
#include "LatencyTuningCallback.h"
#define NANOS_IN_SECOND 1000000000

static long currentTimeInNanos() {

    struct timespec res;
    clock_gettime(CLOCK_MONOTONIC, &res);
    return (res.tv_sec * NANOS_IN_SECOND) + res.tv_nsec;
}

oboe::DataCallbackResult LatencyTuningCallback::onAudioReady(oboe::AudioStream *oboeStream, void *audioData, int32_t numFrames) {

    //time_other = currentTimeInNanos() - time_other;
    //LOGI("Other %ld ns", time_other);
    time_render = currentTimeInNanos();

    if (oboeStream != mStream) {
        mStream = oboeStream;
       //mLatencyTuner = std::make_unique<oboe::LatencyTuner>(*oboeStream);
    }

    auto result = DefaultDataCallback::onAudioReady(oboeStream, audioData, numFrames);

    //time_render = currentTimeInNanos() - time_render;
    //CPU_usage = (float)time_render / 1000.0;
    //LOGI("Time render %f us", CPU_usage);
    //CPU_usage = (float)time_render/(float)(time_other + time_render)* 100.0F;

    //LOGI("render CPU %.4f %%", CPU_usage);
    //time_other = currentTimeInNanos();
    return result;
}


