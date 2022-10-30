#include "SoundGenerator.h"

SoundGenerator::SoundGenerator(int32_t sampleRate, int32_t channelCount) :
        TappableAudioSource(sampleRate, channelCount)
        , mGenerator(std::make_unique<generator>())
        {
    LOGI("_-_ SoundGenerator::SoundGenerator Constructor_-_");
    mGenerator->init();
    LOGI("_-_ SoundGenerator::SoundGenerator sampleRate %d channelCount %d _-_", sampleRate, channelCount );
}

//Рендер звука
void SoundGenerator::renderAudio(float *audioData, int32_t numFrames) {
    mGenerator->renderAudio(audioData, numFrames);
}

void SoundGenerator::tap(bool isOn) {

}

