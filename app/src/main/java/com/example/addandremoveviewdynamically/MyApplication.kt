package com.example.addandremoveviewdynamically

import android.app.Application
import android.content.Context
import com.google.android.material.color.DynamicColors


class MyApplication : Application() {
    override fun onCreate() {
        DynamicColors.applyToActivitiesIfAvailable(this)
        super.onCreate()
        mContext = getApplicationContext()
    }

    companion object {
        private var mContext: Context? = null
        val context: Context?
            get() = mContext
    }
}