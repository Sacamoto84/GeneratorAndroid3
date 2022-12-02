package com.example.generator2.audio_device;

import android.content.Context;
import android.content.res.Resources.Theme;
import android.media.AudioDeviceCallback;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.widget.Spinner;

import com.example.generator2.R;

import java.util.List;

//public class AudioDeviceSpinner {
//
//    private static final int AUTO_SELECT_DEVICE_ID = 0;
//    private static final String TAG = AudioDeviceSpinner.class.getName();
//    private int mDirectionType;
//    private AudioDeviceAdapter mDeviceAdapter;
//    private AudioManager mAudioManager;
//
//    public void setup(Context context){
//        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
//
//        mDeviceAdapter = new AudioDeviceAdapter(context);
//        //setAdapter(mDeviceAdapter);
//
//        // Add a default entry to the list and select it
//        mDeviceAdapter.add(new AudioDeviceListEntry(AUTO_SELECT_DEVICE_ID, context.getString(R.string.auto_select)));
//
//        //setSelection(0);
//
//    }
//
//    public void setDirectionType(int directionType){
//        this.mDirectionType = directionType;
//        setupAudioDeviceCallback();
//    }
//
//    private void setupAudioDeviceCallback(){
//
//        // Note that we will immediately receive a call to onDevicesAdded with the list of
//        // devices which are currently connected.
//        mAudioManager.registerAudioDeviceCallback(new AudioDeviceCallback() {
//            @Override
//            public void onAudioDevicesAdded(AudioDeviceInfo[] addedDevices) {
//
//                List<AudioDeviceListEntry> deviceList = AudioDeviceListEntry.createListFrom(addedDevices, mDirectionType);
//                if (deviceList.size() > 0){
//                    mDeviceAdapter.addAll(deviceList);
//                }
//            }
//
//            public void onAudioDevicesRemoved(AudioDeviceInfo[] removedDevices) {
//
//                List<AudioDeviceListEntry> deviceList =  AudioDeviceListEntry.createListFrom(removedDevices, mDirectionType);
//                for (AudioDeviceListEntry entry : deviceList){
//                    mDeviceAdapter.remove(entry);
//                }
//            }
//        }, null);
//    }
//}
