package com.example.generator2.vm

import com.example.generator2.PlaybackEngine
import com.example.generator2.audio_device.AudioDevice
import com.example.generator2.backup.Backup
import com.example.generator2.screens.firebase.Firebas
import com.example.generator2.screens.scripting.ui.ScriptKeyboard
import com.example.generator2.util.UtilsKT

class Hub(
    var utils: UtilsKT,
    var script: Script,
    var keyboard: ScriptKeyboard,
    var playbackEngine: PlaybackEngine,
    var audioDevice: AudioDevice,
    var firebase: Firebas,
    var backup: Backup
) {


}