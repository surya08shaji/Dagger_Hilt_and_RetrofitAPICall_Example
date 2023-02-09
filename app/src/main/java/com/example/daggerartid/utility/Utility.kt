package com.example.daggerartid.utility

import android.graphics.Paint

object Utility {
    fun getTextWidth(paint: Paint, text: String?): Float {
        return paint.measureText(text)
    }
}