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

#include "generator.h"

#include "signal.h"

class SoundGenerator : public IRenderableAudio {
public:

    SoundGenerator(int32_t sampleRate, int32_t channelCount) {

        LOGI("_-_ SoundGenerator::SoundGenerator Constructor_-_");
        init();
        LOGI("_-_ SoundGenerator::SoundGenerator sampleRate %d channelCount %d _-_", sampleRate,
             channelCount);
    }

    ~SoundGenerator() = default;

    SoundGenerator(SoundGenerator &&other) = default;

    SoundGenerator &operator=(SoundGenerator &&other) = default;

    //Рендер звука
    void renderAudio(float *audioData, int32_t numFrames) override {

        //LOGI("numFrames:%d",numFrames);

        //CH1.Volume = 0.65F;
        //CH2.Volume = 0.55F;

        renderChanel(&CH1, numFrames);
        renderChanel(&CH2, numFrames);

        for (int i = 0; i < numFrames; i++) {
            audioData[i * 2]     = CH1.mBuffer[i];
            audioData[i * 2 + 1] = CH2.mBuffer[i];
        }

    }

    //std::unique_ptr<float[]> mBuffer = std::make_unique<float[]>(4096);

    void renderChanel(_structure_ch * CH, int numFrames) {

        float O = 0.0F;

        CH->rC = (uint32_t) convertHzToR(CH->Carrier_fr);
        CH->rAM = (uint32_t) convertHzToR(CH->AM_fr);
        CH->rFM = (uint32_t) convertHzToR(CH->FM_mod_fr);

        //std::fill_n(CH->mBuffer, numFrames, 0);

        for (int i = 0; i < numFrames; i++) {

            if (CH->CH_EN) {

                if (CH->FM_EN) {
                    CH->phase_accumulator_fm = CH->phase_accumulator_fm + CH->rFM;
                    CH->phase_accumulator_carrier = CH->phase_accumulator_carrier + (uint32_t) convertHzToR(CH->buffer_fm[CH->phase_accumulator_fm >> 22]);
                    //+ (uint32_t) (
                            //(CH->buffer_fm[CH->phase_accumulator_fm >> 22]) * 1000.0F * 3000.0F/33.21F );
                }
                else
                    CH->phase_accumulator_carrier += CH->rC;


                if (CH->AM_EN) {
                    CH->phase_accumulator_mod = CH->phase_accumulator_mod + CH->rAM;

                    O = CH->Volume
                            *  (float)(CH->buffer_carrier[CH->phase_accumulator_carrier >> 22] - 2048.0F)/2048.0F
                            * ((float) CH->buffer_am[CH->phase_accumulator_mod >> 22] / 4095.0F);
                } else
                    O = CH->Volume
                            *  (float)(CH->buffer_carrier[CH->phase_accumulator_carrier >> 22] - 2048.0F)/2048.0F;

            } else
                O = 0;

            CH->mBuffer[i] = O;
        }

    }


    void init() {
        LOGI("-----------------------");
        LOGI("---init()---");
        LOGI("-----------------------");
        int i;
        i = 0;
        CH1.CH_EN = 0;
        CH2.CH_EN = 0;

        for (i = 0; i < 1024; i++) {
            CH1.buffer_carrier[i] = Ramp_1024[i];
            CH2.buffer_carrier[i] = Ramp_1024[i];
            CH1.buffer_am[i] = Ramp_1024[i];
            CH2.buffer_am[i] = Ramp_1024[i];
        }
        CH1.Carrier_fr = 1000;
        CH2.Carrier_fr = 1000;
        CH1.AM_fr = 10.0;
        CH2.AM_fr = 10.0;
        for (i = 0; i < 1024; i++) {
            CH1.buffer_fm[i] = 2500;
            CH2.buffer_fm[i] = 2500;
        }

        CH1.Volume = 0.65F;
        CH2.Volume = 0.55F;

    }

    //std::unique_ptr<uint16_t[]> buffer_carrier1 = std::make_unique<uint16_t[]>(1024);

    float convertHzToR(float hz) {
        hz = hz * 16384.0F / 3.798F * 2.0F * 1000.0 / 48.8 / 2.0 * 1000.0 / 988.0;
        return hz;
    }

    void CreateFM_CH1(void) {
        int x, y;
        int i = 0;
        x = CH1.FM_Base - CH1.FM_Dev;
        y = CH1.FM_Dev * 2;
        for (i = 0; i < 1024; i++)
            CH1.buffer_fm[i] = x + (y * CH1.source_buffer_fm[i] / 4095.0F);
    }

    void CreateFM_CH2(void) {
        int x, y;
        int i = 0;
        x = CH2.FM_Base - CH2.FM_Dev;
        y = CH2.FM_Dev * 2;
        for (i = 0; i < 1024; i++)
            CH2.buffer_fm[i] = x + (y * CH2.source_buffer_fm[i] / 4095.0F);
    }
};


#endif
