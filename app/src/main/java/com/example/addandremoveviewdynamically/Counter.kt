package com.example.addandremoveviewdynamically

import android.graphics.Color
import android.widget.TextView
import android.widget.Toast
import com.akki.circlemenu.OnCircleMenuItemClicked

class Counter {
    fun touchedCounter(id: Int): Int {
        var selectedNumber = 0
        when (id) {
            R.drawable.number_0 -> selectedNumber = 0
            R.drawable.number_1 -> selectedNumber = 1
            R.drawable.number_2 -> selectedNumber = 2
            R.drawable.number_3 -> selectedNumber = 3
            R.drawable.number_4 -> selectedNumber = 4
            R.drawable.number_5 -> selectedNumber = 5
            R.drawable.number_6 -> selectedNumber = 6
            R.drawable.number_7 -> selectedNumber = 7
            R.drawable.number_8 -> selectedNumber = 8
            R.drawable.number_9 -> selectedNumber = 9
        }
        return selectedNumber
    }
}