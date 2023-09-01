package com.example.addandremoveviewdynamically

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool

class SoundEffect(context: Context) {
    val audioAttributes =
        AudioAttributes
            .Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
    //
    val soundPool = SoundPool
        .Builder()
        .setMaxStreams(10)
        .setAudioAttributes(audioAttributes)
        .build()

    var soundCorrectBlockMove = soundPool.load(context , R.raw.alert_high_intensity, 1)
    var soundIncorrectBlockMove = soundPool.load(context, R.raw.navigation_transition_right, 1)
    var soundETenGroupMade = soundPool.load(context, R.raw.hero_decorative_celebration_01, 1)
    var soundSetup         = soundPool.load(context, R.raw.notification_decorative_01, 1)

    fun playSoundETenGroupMade(){
        soundPool.play(soundETenGroupMade, 1F, 1F, 0, 0, 1F)
    }

    fun playSoundCorrectBlockMove(){
        soundPool.play(soundCorrectBlockMove, 1F, 1F, 0, 0, 1F)
    }

    fun playSoundIncorrectBlockMove(){
        soundPool.play(soundIncorrectBlockMove, 1F, 1F, 0, 0, 1F)
    }

    fun playSoundSetup(){
        soundPool.play(soundSetup, 1F, 1F, 0, 0, 1F)
    }
}