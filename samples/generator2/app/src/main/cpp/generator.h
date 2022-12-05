#ifndef SAMPLES_GENERATOR_H
#define SAMPLES_GENERATOR_H

#include <cstdint>
#include <atomic>
#include <math.h>
#include <memory>

#include "signal.h"

#include "logging_macros.h"

#include <Oscillator.h>

typedef struct {

    bool CH_EN;
    int AM_EN;
    int FM_EN;

    float Carrier_fr;         //Частота нечущей

    float AM_fr;              //Часта модуляции

    float FM_Base;
    float FM_Dev;
    char  FM_mod[20];       //Имя файла
    float FM_mod_fr;        //Часта модуляции

    //Буфферы



    uint16_t buffer_carrier[1024];
    uint16_t buffer_am[1024];
    uint16_t buffer_fm[1024];

    uint16_t source_buffer_fm[1024]; //Используется для перерасчета модуляции

    uint32_t rC;		     //Частота несущей 1
    uint32_t angle_cr;

    uint32_t rAM;
    uint32_t angle_m;

    uint32_t rFM;
    uint32_t angle_fm;
    float Volume; //Громкость 0..1

    //Аккумуляторы
    uint32_t phase_accumulator_carrier;
    uint32_t phase_accumulator_mod;
    uint32_t phase_accumulator_fm;

} _structure_ch;

class generator{
public:

    _structure_ch CH1;
    _structure_ch CH2;

    generator()
    {
        LOGI("----------------------------------------");
        LOGI("---generator::generator() конструктор---");
        LOGI("----------------------------------------");


    }
    ~generator() = default;

    void init(void);

    void renderAudio(float *audioData, int32_t numFrames);

    float convertHzToR(float hz) {
        hz = hz * 16384.0F / 3.798F * 2.0F    * 1000.0/48.8 / 2.0 * 1000.0/988.0;
        return hz;
    }
    float convertHzToR_FM(float hz) {
        hz = hz * 16384.0F;
        return hz;
    }
    float convertRToHz(float hz) {
        hz = hz / 16384.0F * 3.798F / 2.0F;
        return hz;
    }

    void CreateFM_CH1(void)
    {
        int x, y;
        int i = 0;
        x = CH1.FM_Base - CH1.FM_Dev;
        y = CH1.FM_Dev * 2;
        for (i = 0; i < 1024; i++)
            CH1.buffer_fm[i] =  x + (y * CH1.source_buffer_fm[i] / 4095.0F);
    }

    void CreateFM_CH2(void)
    {
        int x, y;
        int i = 0;
        x = CH2.FM_Base - CH2.FM_Dev;
        y = CH2.FM_Dev * 2;
        for (i = 0; i < 1024; i++)
            CH2.buffer_fm[i] =  x + (y * CH2.source_buffer_fm[i] / 4095.0F);
    }

    std::unique_ptr<uint16_t[]> buffer_carrier1 = std::make_unique<uint16_t[]>(1024);
    std::unique_ptr<uint16_t[]> buffer_carrier2 = std::make_unique<uint16_t[]>(1024);

    std::unique_ptr<uint16_t[]> buffer_am1 = std::make_unique<uint16_t[]>(1024);
    std::unique_ptr<uint16_t[]> buffer_am2 = std::make_unique<uint16_t[]>(1024);

    std::unique_ptr<uint16_t[]> buffer_fm1 = std::make_unique<uint16_t[]>(1024);
    std::unique_ptr<uint16_t[]> buffer_fm2 = std::make_unique<uint16_t[]>(1024);
private:
//int steamSampleRate;
//unsigned int buffer_temp[1024+16]; //Сохраняются данные файлов

    //static constexpr size_t kSharedBufferSize = 1024;
    //std::unique_ptr<Oscillator> mOscillators;
    //std::unique_ptr<float[]> mBuffer = std::make_unique<float[]>(kSharedBufferSize);



};


#endif
