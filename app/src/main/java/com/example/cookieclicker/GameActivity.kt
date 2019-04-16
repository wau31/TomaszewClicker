package com.example.cookieclicker

import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Chronometer
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {

    private var controller = GameController(this)

    private lateinit var chronometer: Chronometer
    private var chronometerRunning = false;
    var chronometerPauseOffset = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        chronometer = findViewById(R.id.chronometer)

        finnishActivity()

        cookieButton1.setOnClickListener {
            controller.GrantPoints(controller.list[0])
        }

        cookieButton2.setOnLongClickListener {
            controller.GrantPoints(controller.list[1])
            return @setOnLongClickListener true
        }


        startButton.setOnClickListener {
            startActivity()
        }
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    fun startActivity() {

        startButton.isEnabled = false;
        startButton.visibility = View.GONE

        cookieButton1.isEnabled = true;
        cookieButton2.isEnabled = true;

        startChronometer()


    }

    fun finnishActivity() {

        startButton.isEnabled = false;
        startButton.visibility = View.VISIBLE

        cookieButton1.isEnabled = true;
        cookieButton2.isEnabled = true;

        stopChronometer()


    }

    fun startChronometer() {
        if (!chronometerRunning) {
            chronometer.base = SystemClock.elapsedRealtime() - chronometerPauseOffset
            chronometer.start()
            chronometerRunning = true
        }
    }

    fun stopChronometer() {
        if (chronometerRunning) {
            chronometer.stop()
            chronometerPauseOffset = SystemClock.elapsedRealtime() - chronometer.base
            chronometerRunning = false
        }
    }

}