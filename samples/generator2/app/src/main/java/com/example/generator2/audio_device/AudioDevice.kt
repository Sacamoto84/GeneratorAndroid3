package com.example.generator2.audio_device

import android.content.Context
import android.media.AudioDeviceCallback
import android.media.AudioDeviceInfo
import android.media.AudioManager
import androidx.compose.runtime.mutableStateListOf
import com.example.generator2.R

class AudioDevice (private var context: Context) {

    private var mDirectionType = 0
    //private var mDeviceAdapter: AudioDeviceAdapter? = null
    private var mAudioManager: AudioManager? = null

    //private var mDeviceAdapter: MutableList<AudioDeviceListEntry> = mutableListOf()

    var mDeviceAdapter = mutableStateListOf<AudioDeviceListEntry>()

    init {

        println("┌----------------------┐")
        println("│  AudioDevice init{}  │")
        println("└----------------------┘")

        mAudioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        //mDeviceAdapter = AudioDeviceAdapter(context)
        mDeviceAdapter.add(
            AudioDeviceListEntry(0, context.getString(R.string.auto_select))
        )

        mDirectionType = AudioManager.GET_DEVICES_OUTPUTS
        setupAudioDeviceCallback()

    }


    private fun setupAudioDeviceCallback() {

        // Note that we will immediately receive a call to onDevicesAdded with the list of
        // devices which are currently connected.
        mAudioManager!!.registerAudioDeviceCallback(object : AudioDeviceCallback() {

            override fun onAudioDevicesAdded(addedDevices: Array<AudioDeviceInfo>) {

                val deviceList = AudioDeviceListEntry.createListFrom(addedDevices, mDirectionType)

                if (deviceList.size > 0) {
                    mDeviceAdapter.addAll(deviceList)
                }

                println("┌----------------------------------------------------------------------------┐")
                println("│  onAudioDevicesAdded                                                       │")
                println("├----------------------------------------------------------------------------┘")
                println( "│ "+ deviceList.joinToString(", "))
                println("└----------------------------------------------------------------------------┘")

            }

            override fun onAudioDevicesRemoved(removedDevices: Array<AudioDeviceInfo>) {
                val deviceList = AudioDeviceListEntry.createListFrom(removedDevices, mDirectionType)
                for (entry in deviceList) {
                    mDeviceAdapter.remove(entry)
                }
            }

        }, null)
    }

}