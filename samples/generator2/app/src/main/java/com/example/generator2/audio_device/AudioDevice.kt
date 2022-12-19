package com.example.generator2.audio_device

import android.content.Context
import android.media.AudioDeviceCallback
import android.media.AudioDeviceInfo
import android.media.AudioManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.generator2.PlaybackEngine
import com.example.generator2.R
import com.example.generator2.util.UtilsKT
import com.example.generator2.vm.LiveData
import com.example.generator2.vm.Script
import com.example.generator2.vm.StateCommandScript
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
class AudioDevice(private var context: Context, var playbackEngine: PlaybackEngine, var script : Script, var utils: UtilsKT) {

    private var mDirectionType = 0

    //private var mDeviceAdapter: AudioDeviceAdapter? = null
    private var mAudioManager: AudioManager? = null

    //private var mDeviceAdapter: MutableList<AudioDeviceListEntry> = mutableListOf()

    var mDeviceAdapter = mutableStateListOf<AudioDeviceListEntry>()

    var mScoStarted = false

    var mDeviceId by mutableStateOf(0)

    init {

        playbackEngine.create()
        playbackEngine.start()

        println("┌----------------------┐")
        println("│  AudioDevice init{}  │")
        println("└----------------------┘")

        mAudioManager =
            context.getSystemService(Context.AUDIO_SERVICE) as AudioManager //mDeviceAdapter = AudioDeviceAdapter(context)
        mDeviceAdapter.add(
            AudioDeviceListEntry(0, context.getString(R.string.auto_select))
        )

        mDirectionType = AudioManager.GET_DEVICES_OUTPUTS

        GlobalScope.launch (Dispatchers.IO){
            setupAudioDeviceCallback()
        }

    }

    fun getDeviceId()
    {
        mDeviceId = playbackEngine.getAudioDeviceId()
    }



    private fun setupAudioDeviceCallback() {

        // Note that we will immediately receive a call to onDevicesAdded with the list of
        // devices which are currently connected.
        mAudioManager!!.registerAudioDeviceCallback(object : AudioDeviceCallback() {

            override fun onAudioDevicesAdded(addedDevices: Array<AudioDeviceInfo>) {

                //script.command( StateCommandScript.STOP )

                val deviceList = AudioDeviceListEntry.createListFrom(addedDevices, mDirectionType)

                if (deviceList.size > 0) {
                    mDeviceAdapter.addAll(deviceList)
                }

                println("┌----------------------------------------------------------------------------┐")
                println("│  onAudioDevicesAdded                                                       │")
                println("├----------------------------------------------------------------------------┘")
                println(deviceList.joinToString("\n"))
                println("└----------------------------------------------------------------------------┘")

                mDeviceId = playbackEngine.getAudioDeviceId()


            }

            override fun onAudioDevicesRemoved(removedDevices: Array<AudioDeviceInfo>) {

                //script.command(StateCommandScript.STOP)

                val deviceList = AudioDeviceListEntry.createListFrom(removedDevices, mDirectionType)
                for (entry in deviceList) {
                    mDeviceAdapter.remove(entry)
                }

                println("┌----------------------------------------------------------------------------┐")
                println("│  onAudioDevicesRemoved                                                     │")
                println("├----------------------------------------------------------------------------┘")
                println(deviceList.joinToString(", "))
                println("└----------------------------------------------------------------------------┘")

                mDeviceId = playbackEngine.getAudioDeviceId()

            }

        }, null)
    }


    fun OnItemSelectedListener(i: Int) {
         println("Изменить устройство вывода i:${i}")
        // Start Bluetooth SCO if needed.
        if (isScoDevice(getPlaybackDeviceId(i)) && !mScoStarted) {
            startBluetoothSco();
            mScoStarted = true;
            println("Start Bluetooth SCO")
        } else if (!isScoDevice(getPlaybackDeviceId(i)) && mScoStarted) {
            stopBluetoothSco();
            mScoStarted = false;
            println("Stop Bluetooth SCO")
        }

        println("id : ${getPlaybackDeviceId(i)}")

        playbackEngine.setAudioDeviceId(getPlaybackDeviceId(i));


    }

    private fun getPlaybackDeviceId(i: Int): Int {
        return mDeviceAdapter[i].id
    }

    /**
     * @param deviceId
     * @return true if the device is TYPE_BLUETOOTH_SCO
     */
    private fun isScoDevice(deviceId: Int): Boolean {
        if (deviceId == 0) return false // Unspecified
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager?
        val devices: Array<AudioDeviceInfo> =
            audioManager!!.getDevices(AudioManager.GET_DEVICES_OUTPUTS)
        for (device in devices) {
            if (device.id == deviceId) {
                return device.type == AudioDeviceInfo.TYPE_BLUETOOTH_SCO
            }
        }
        return false
    }

    private fun startBluetoothSco() {
        val myAudioMgr = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager?
        myAudioMgr!!.startBluetoothSco()
    }

    private fun stopBluetoothSco() {
        val myAudioMgr = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager?
        myAudioMgr!!.stopBluetoothSco()
    }

    fun sendAlltoGen() {
        println("global sendAlltoGen()")
        playbackEngine.setVolume    (0, LiveData.volume0.value )
        playbackEngine.setVolume    (1, LiveData.volume1.value )
        playbackEngine.setEN        (0, LiveData.ch1_EN.value )
        playbackEngine.setEN        (1, LiveData.ch2_EN.value )
        playbackEngine.setAM_EN     (0, LiveData.ch1_AM_EN.value )
        playbackEngine.setAM_EN     (1, LiveData.ch2_AM_EN.value )
        playbackEngine.setFM_EN     (0, LiveData.ch1_FM_EN.value )
        playbackEngine.setFM_EN     (1, LiveData.ch2_FM_EN.value )
        playbackEngine.setCarrier_fr(0, LiveData.ch1_Carrier_Fr.value )
        playbackEngine.setCarrier_fr(1, LiveData.ch2_Carrier_Fr.value )
        playbackEngine.setAM_fr     (0, LiveData.ch1_AM_Fr.value )
        playbackEngine.setAM_fr     (1, LiveData.ch2_AM_Fr.value )
        playbackEngine.setFM_Base   (0, LiveData.ch1_FM_Base.value )
        playbackEngine.setFM_Base   (1, LiveData.ch2_FM_Base.value )
        playbackEngine.setFM_Dev    (0, LiveData.ch1_FM_Dev.value )
        playbackEngine.setFM_Dev    (1, LiveData.ch2_FM_Dev.value )
        playbackEngine.setFM_fr     (0, LiveData.ch1_FM_Fr.value )
        playbackEngine.setFM_fr     (1, LiveData.ch2_FM_Fr.value )

        utils.Spinner_Send_Buffer("CH0", "CR", LiveData.ch1_Carrier_Filename.value )
        utils.Spinner_Send_Buffer("CH0", "AM", LiveData.ch1_AM_Filename.value )
        utils.Spinner_Send_Buffer("CH0", "FM", LiveData.ch1_FM_Filename.value )
        utils.Spinner_Send_Buffer("CH1", "CR", LiveData.ch2_Carrier_Filename.value )
        utils.Spinner_Send_Buffer("CH1", "AM", LiveData.ch2_AM_Filename.value )
        utils.Spinner_Send_Buffer("CH1", "FM", LiveData.ch2_FM_Filename.value )

        playbackEngine.setMono(LiveData.mono.value)
        playbackEngine.setInvertPhase(LiveData.invert.value)

    }

}


