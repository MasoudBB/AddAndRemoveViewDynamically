package com.example.addandremoveviewdynamically

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool

class SoundManager(context: Context) {
    private val audioAttributes =
        AudioAttributes
            .Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
    //
    private val soundPool = SoundPool
        .Builder()
        .setMaxStreams(10)
        .setAudioAttributes(audioAttributes)
        .build()

    private var soundCorrectMovement = soundPool.load(context, R.raw.alert_high_intensity, 1)
    private var soundIncorrectMovement = soundPool.load(context, R.raw.navigation_transition_right, 1)
    private var soundTenMade = soundPool.load(context, R.raw.hero_decorative_celebration_01, 1)
    private var soundSetup         = soundPool.load(context, R.raw.notification_decorative_01, 1)

    fun playSoundTenMade(){
        soundPool.play(soundTenMade, 1F, 1F, 0, 0, 1F)
    }

    fun playSoundCorrectMovement(){
        soundPool.play(soundCorrectMovement, 1F, 1F, 0, 0, 1F)
    }

    fun playSoundIncorrectMovement(){
        soundPool.play(soundIncorrectMovement, 1F, 1F, 0, 0, 1F)
    }

    fun playSoundSetup(){
        soundPool.play(soundSetup, 1F, 1F, 0, 0, 1F)
    }
}