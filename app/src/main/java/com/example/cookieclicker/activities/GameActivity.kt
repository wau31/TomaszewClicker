package com.example.cookieclicker.activities

import android.os.Bundle
import android.os.SystemClock
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.example.cookieclicker.controllers.GameController
import com.example.cookieclicker.R
import kotlinx.android.synthetic.main.activity_game.*

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

    lateinit var chronometer: Chronometer
    private var chronometerRunning = false
    private var chronometerPauseOffset = 0L



    private lateinit var leftImage:ImageView
    private lateinit var rightImage:ImageView
    private lateinit var spinner:Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        leftImage=findViewById(R.id.LeftImageView)
        rightImage=findViewById(R.id.RightImageView)

        spinner=findViewById(R.id.spinner)
        val adapter=ArrayAdapter(this,
            R.layout.support_simple_spinner_dropdown_item,controller.list)
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        spinner.adapter=adapter

        spinner.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var item=parent?.getItemAtPosition(position)
                //controller.Upgrade()
            }
        }

        controller.onPointsChanged.plusAssign { scoreCurrent.text = controller.score.toString() }
        controller.onGameFinished.plusAssign { finnishActivity() }

        chronometer = findViewById(R.id.chronometer)

        cookieButton1.setOnClickListener {
            controller.grantPoints(controller.list[0])
            handleAnimation(leftImage)
            System.out.println("Simple click")
        }

        cookieButton2.setOnLongClickListener {
            controller.grantPoints(controller.list[1])
            handleAnimation(rightImage)
            System.out.println("Long click")
            return@setOnLongClickListener true
        }

        startButton.setOnClickListener {
            startActivity()
        }

        finnishActivity()
    }


    override fun onPause() {
        super.onPause()
        if(!PreferenceManager.getDefaultSharedPreferences(this).getBoolean("RunInBackground",false))
        {
            stopChronometer()
        }
    }

    override fun onResume() {
        super.onResume()
        startChronometer()
    }

    private fun handleAnimation(ImageView:ImageView){
        ImageView.clearAnimation()
        ImageView.animate().translationYBy(-300f).scaleX(1.2f).scaleY(1.2f).alpha(0f).duration=500

    }

    private fun startActivity() {
        controller.checkForHighScore()
        startButton.isEnabled = false
        startButton.visibility = View.GONE

        cookieButton1.isEnabled = true
        cookieButton1.visibility=View.VISIBLE

        cookieButton2.isEnabled = true
        cookieButton2.visibility=View.VISIBLE

        startChronometer()


    }

    private fun finnishActivity() {

        startButton.isEnabled = true
        startButton.visibility = View.VISIBLE

        cookieButton1.isEnabled = false
        cookieButton1.visibility=View.GONE

        cookieButton2.isEnabled = false
        cookieButton2.visibility=View.GONE

        stopChronometer()

    }

    private fun startChronometer() {
        if (!chronometerRunning) {
            chronometer.base = SystemClock.elapsedRealtime() - chronometerPauseOffset
            chronometer.start()
            chronometerRunning = true
        }
    }

    private fun stopChronometer() {
        if (chronometerRunning) {
            chronometer.stop()
            chronometerPauseOffset = SystemClock.elapsedRealtime() - chronometer.base
            chronometerRunning = false
        }
    }

}