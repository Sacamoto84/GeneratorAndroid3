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

    float mBuffer[4096];

} _structure_ch;

extern _structure_ch CH1;
extern _structure_ch CH2;

extern bool Mono;
extern bool Invert;

extern void setToMono();   // Перевод в режим моно
extern void setToStereo(); // Перевод в режим стерео

extern void resetCarrierPhase();    //Сброс фазы несущей
extern void resetAllPhase();


#endif
