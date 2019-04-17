package com.example.cookieclicker

import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Chronometer
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_game.*
import java.io.Console

class GameActivity : AppCompatActivity() {
    /*concepts of upgrades:
    - single click: code in Java, upgrade to Java 8,11,12
    - long click: code in C#, upgrade .net 2,3,4
    - over time bonus enable IntelliSense
    - +X sec +Y points DeploySolution
    - timer -X sec git reset --hard
    - -X sec -Y points CodeReview

    */
    private var controller = GameController(this)

    private lateinit var chronometer: Chronometer
    private var chronometerRunning = false;
    var chronometerPauseOffset = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val spinner:Spinner=findViewById(R.id.spinner)
        val aa=ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,controller.list)
        aa.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        spinner.adapter=aa

        chronometer = findViewById(R.id.chronometer)

        finnishActivity()

        cookieButton1.setOnClickListener {
            controller.GrantPoints(controller.list[0])
            System.out.println("Simple click")
        }

        cookieButton2.setOnLongClickListener {
            controller.GrantPoints(controller.list[1])
            System.out.println("Long click")
            return@setOnLongClickListener true
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
        cookieButton1.visibility=View.VISIBLE

        cookieButton2.isEnabled = true;
        cookieButton2.visibility=View.VISIBLE


        controller.onPointsChanged.plusAssign { scoreCurrent.text = controller.score.toString() }


        startChronometer()


    }

    fun finnishActivity() {

        startButton.isEnabled = true;
        startButton.visibility = View.VISIBLE

        cookieButton1.isEnabled = false;
        cookieButton1.visibility=View.GONE

        cookieButton2.isEnabled = false;
        cookieButton2.visibility=View.GONE

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