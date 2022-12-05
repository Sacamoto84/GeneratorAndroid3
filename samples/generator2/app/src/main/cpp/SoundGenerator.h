/*
 * Copyright 2018 The Android Open Source Project
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

#ifndef SAMPLES_SOUNDGENERATOR_H
#define SAMPLES_SOUNDGENERATOR_H

#include <logging_macros.h>

#include <Oscillator.h>
//#include <TappableAudioSource.h>
#include "generator.h"


class SoundGenerator : public IRenderableAudio
{
public:

    SoundGenerator(int32_t sampleRate, int32_t channelCount) {

        mGenerator = std::make_unique<generator>();
        LOGI("_-_ SoundGenerator::SoundGenerator Constructor_-_");
        mGenerator->init();
        LOGI("_-_ SoundGenerator::SoundGenerator sampleRate %d channelCount %d _-_", sampleRate,
             channelCount);
    }

    ~SoundGenerator() = default;

    SoundGenerator(SoundGenerator &&other) = default;

    SoundGenerator &operator=(SoundGenerator &&other) = default;

    //void renderAudio(float *audioData, int32_t numFrames) override;

    //Мои функции
    std::unique_ptr<generator> mGenerator;

    //Рендер звука
    void renderAudio(float *audioData, int32_t numFrames) override {

        for (int j = 0; j < 1024; j++) {
           mGenerator->buffer_carrier1[j] = mGenerator->CH1.buffer_carrier[j];
        }

        mGenerator->renderAudio(audioData, numFrames);
    }


private:

    //std::unique_ptr<Oscillator[]> mOscillators;
    //std::unique_ptr<float[]> mBuffer = std::make_unique<float[]>(kSharedBufferSize);
};


#endif
