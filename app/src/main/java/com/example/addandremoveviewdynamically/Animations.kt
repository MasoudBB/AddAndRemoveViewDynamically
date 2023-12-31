package com.example.addandremoveviewdynamically

import android.text.Editable
import android.view.View
import android.view.animation.AnimationUtils

class Animations {
    var animated:Boolean = false
    fun zoom(view: View) {
        val animation = AnimationUtils.loadAnimation(
            MyApplication.context,
            R.anim.zoom_animation
        )
        view.startAnimation(animation)
    }

    fun zoomReverse(view: View) {
        val animation = AnimationUtils.loadAnimation(
            MyApplication.context,
            R.anim.revers_zoom_animation
        )
        view.startAnimation(animation)
    }


    fun fade(view: View) {
        animated =true
        val animation = AnimationUtils.loadAnimation(
            MyApplication.context,
            R.anim.fade_animation
        )
        view.startAnimation(animation)
        animated
    }

    fun reversFade(view: View) {
        val animation = AnimationUtils.loadAnimation(
            MyApplication.context,
            R.anim.revers_fade_animation
        )
        view.startAnimation(animation)
    }


    fun move(view: View) {
        val animation = AnimationUtils.loadAnimation(
            MyApplication.context,
            R.anim.move_animation
        )
        view.startAnimation(animation)
    }

    fun slide(view:View){
        val animation = AnimationUtils.loadAnimation(
            MyApplication.context,
            R.anim.slide_animation
        )
        view.startAnimation(animation)
    }

    fun blink(view: View){
        val animation = AnimationUtils.loadAnimation(
            MyApplication.context,
            R.anim.blink_animation
        )
        view.startAnimation(animation)
    }

    fun bounce(view: View){
        val animation =AnimationUtils.loadAnimation(
            MyApplication.context,
            R.anim.bounce
        )
        view.startAnimation(animation)
    }
    fun stop(view: View){
     view.clearAnimation()
    }

}