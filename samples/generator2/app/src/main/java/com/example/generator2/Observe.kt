package com.example.generator2

import android.util.Log
import com.example.generator2.PlaybackEngine
import com.example.generator2.Utils
import com.example.generator2.vmLiveData
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


class Observe (private var liveData: vmLiveData, private var playbackEngine: PlaybackEngine, private var  utils: UtilsKT)
{

    fun observe() {


        liveData.ch1_EN.observeForever { ch1_EN ->
            Log.d("observeForever", "onClick")
            playbackEngine.CH_EN(0, ch1_EN!!)
        }

        liveData.ch2_EN.observeForever { ch2_EN ->
            Log.d("observeForever", "onClick")
            playbackEngine.CH_EN(1, ch2_EN!!)
        }

        liveData.ch1_AM_EN.observeForever { ch1_AM_EN ->
            Log.d("observeForever", "onClick")
            playbackEngine.CH_AM_EN(0, ch1_AM_EN!!)
        }

        liveData.ch2_AM_EN.observeForever { ch2_AM_EN ->
            Log.d("observeForever", "onClick")
            playbackEngine.CH_AM_EN(1, ch2_AM_EN!!)
        }

        liveData.ch1_FM_EN.observeForever { ch1_FM_EN ->
            Log.d("observeForever", "onClick")
            playbackEngine.CH_FM_EN(0, ch1_FM_EN!!)
        }

        liveData.ch2_FM_EN.observeForever { ch2_FM_EN ->
            Log.d("observeForever", "onClick")
            playbackEngine.CH_FM_EN(1, ch2_FM_EN!!)
        }


        liveData.ch1_Carrier_Fr.observeForever { ch1_Carrier_Fr ->
            Log.d("observeForever", "onClick")
            val fr = ch1_Carrier_Fr.toInt().toFloat()
            playbackEngine.CH_Carrier_fr(0, fr)
        }

        liveData.ch2_Carrier_Fr.observeForever { ch2_Carrier_Fr ->
            Log.d("observeForever", "onClick")
            val fr = ch2_Carrier_Fr.toInt().toFloat()
            playbackEngine.CH_Carrier_fr(1, fr)
        }

        liveData.ch1_AM_Fr.observeForever { ch1_AM_Fr ->
            Log.d("observeForever", "onClick")
            playbackEngine.CH_AM_fr(0, ch1_AM_Fr!!)
        }

        liveData.ch2_AM_Fr.observeForever { ch2_AM_Fr ->
            Log.d("observeForever", "onClick")
            playbackEngine.CH_AM_fr(1, ch2_AM_Fr!!)
        }

        liveData.ch1_FM_Base.observeForever { ch1_FM_Base ->
            val fr = ch1_FM_Base.toInt().toFloat()
            Log.d("observeForever", "onClick")
            playbackEngine.CH_FM_Base(0, fr)
        }

        liveData.ch2_FM_Base.observeForever { ch2_FM_Base ->
            val fr = ch2_FM_Base.toInt().toFloat()
            Log.d("observeForever", "onClick")
            playbackEngine.CH_FM_Base(1, fr)
        }

        liveData.ch1_FM_Dev.observeForever { ch1_FM_Dev ->
            val fr = ch1_FM_Dev.toInt().toFloat()
            Log.d("observeForever", "onClick")
            playbackEngine.CH_FM_Dev(0, fr)
        }

        liveData.ch2_FM_Dev.observeForever { ch2_FM_Dev ->
            val fr = ch2_FM_Dev.toInt().toFloat()
            Log.d("observeForever", "onClick")
            playbackEngine.CH_FM_Dev(1, fr)
        }

        liveData.ch1_FM_Fr.observeForever { ch1_FM_Fr ->
            Log.d("observeForever", "onClick")
            playbackEngine.CH_FM_fr(0, ch1_FM_Fr!!)
        }

        liveData.ch2_FM_Fr.observeForever { ch2_FM_Fr ->
            Log.d("observeForever", "onClick")
            playbackEngine.CH_FM_fr(1, ch2_FM_Fr!!)
        }

        liveData.ch1_Carrier_Filename.observeForever { name ->
            Log.d("observeForever", "onClick")
            utils.Spinner_Send_Buffer(
                "CH0", "CR", name
            ) //Читае м отсылаем массив
        }

        liveData.ch2_Carrier_Filename.observeForever { name ->
            Log.d("observeForever", "onClick")
            utils.Spinner_Send_Buffer(
                "CH1", "CR", name
            ) //Читае м отсылаем массив
        }

        liveData.ch1_AM_Filename.observeForever { name ->
            Log.d("observeForever", "onClick")
            utils.Spinner_Send_Buffer(
                "CH0", "AM", name
            ) //Читае м отсылаем массив
        }

        liveData.ch2_AM_Filename.observeForever { name ->
            Log.d("observeForever", "onClick")
            utils.Spinner_Send_Buffer(
                "CH1", "AM", name
            ) //Читае м отсылаем массив
        }

        liveData.ch1_FM_Filename.observeForever { name ->
            Log.d("observeForever", "onClick")
            utils.Spinner_Send_Buffer(
                "CH0", "FM", name
            ) //Читае м отсылаем массив
        }

        liveData.ch2_FM_Filename.observeForever { name ->
            Log.d("observeForever", "onClick")
            utils.Spinner_Send_Buffer(
                "CH1", "FM", name
            ) //Читае м отсылаем массив
        }

    }




}