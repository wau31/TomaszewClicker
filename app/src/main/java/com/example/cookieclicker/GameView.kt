package com.example.cookieclicker

import android.R
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.view.View

class GameView(context: Context): View(context){
    override fun draw(canvas: Canvas?){
        super.draw(canvas)
        canvas?.drawColor(Color.LTGRAY)
    }
}