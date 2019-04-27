package com.example.cookieclicker.activities

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.os.SystemClock
import android.preference.PreferenceManager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.*
import com.example.cookieclicker.controllers.GameController
import com.example.cookieclicker.R
import com.example.cookieclicker.controllers.bonusGenerators.IBonusGenerator
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

    private lateinit var controller: GameController
    private lateinit var chronometer: Chronometer
    private var chronometerRunning = false
    private var chronometerPauseOffset = 0L


    private lateinit var leftImage: ImageView
    private lateinit var rightImage: ImageView
    private lateinit var spinner: Spinner

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        leftImage = findViewById(R.id.LeftImageView)
        rightImage = findViewById(R.id.RightImageView)

        setupController()

        val popup = setupUpgradeList()
        upgradeButton.setOnClickListener {
            popup.showAsDropDown(it, -5, 0)
        }
        setupButtons()


        controller.onPointsChanged.plusAssign { scoreCurrent.text = controller.score.toString() }
        controller.onGameFinished.plusAssign { finnishActivity() }

        displayIntroductionPopup()
    }

    private fun setupButtons() {
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
    }


    private fun setupUpgradeList(): PopupWindow {
        val popupWindow = PopupWindow(this)

        val listView = ListView(this)
        listView.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, controller.list)
        listView.setOnItemClickListener { parent, view, position, id ->

            val fadeInAnimation = AnimationUtils.loadAnimation(view.context, android.R.anim.fade_in)
            fadeInAnimation.duration = 10
            popupWindow.dismiss()
            val item = parent?.getItemAtPosition(position)
            if (item is IBonusGenerator) {
                controller.upgrade(item)
            }
        }

        popupWindow.isFocusable = true
        popupWindow.width
        popupWindow.height = WindowManager.LayoutParams.WRAP_CONTENT
        //popupWindow.setBackgroundDrawable(getDrawable(resources.))
        popupWindow.contentView = listView
        return popupWindow

    }

    private fun setupController() {
        chronometer = findViewById(R.id.chronometer)
        controller = GameController(this, chronometer)
        controller.setup()
        //chronometer.setOnChronometerTickListener { controller.grantPoints(controller.list[2]) }
        controller.onTimeModified.plusAssign { modifyChronometerTime(it) }

    }

    private fun displayIntroductionPopup() {
        val dialog = AlertDialog.Builder(this)
        val alertView = layoutInflater.inflate(R.layout.dialog_username, null)
        val username: EditText = alertView.findViewById(R.id.username_text)
        val start = alertView.findViewById<Button>(R.id.start)
        val alertDialog = dialog.setView(alertView).create()

        start.setOnClickListener {
            if (!username.text.toString().isEmpty()) {
                controller.username = username.text.toString()
            }
            startActivity()
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    private fun displayGameOverPopup() {
        val dialog = AlertDialog.Builder(this)
        val alertView = layoutInflater.inflate(R.layout.dialog_gameover, null)
        val toMenu = alertView.findViewById<Button>(R.id.tomenu)
        val alertDialog = dialog.setView(alertView).create()
        toMenu.setOnClickListener {
            startActivity(
                Intent(this, MainActivity::class.java),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            )
        }
        alertDialog.show()
    }

    override fun onStart() {
        super.onStart()
        startChronometer()

    }

    override fun onPause() {
        super.onPause()
        if (!PreferenceManager.getDefaultSharedPreferences(this).getBoolean("RunInBackground", false)) {
            stopChronometer()
        }
    }

    override fun onResume() {
        super.onResume()
        startChronometer()
    }

//    private fun handleAnimation(ImageView: ImageView) {
//        ImageView.clearAnimation()
//        ImageView.animate().translationYBy(-300f).scaleX(1.2f).scaleY(1.2f).alpha(0f).duration = 500
//
//    }

    private fun handleAnimation(ImageView: ImageView) {
        val displayMetrics=DisplayMetrics()
        val display = windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height=displayMetrics.heightPixels.toFloat()

        val yAnimator = ValueAnimator.ofFloat(0f, -height/2)
        val alphaAnimator =ValueAnimator.ofFloat(0f,1f)
        val scaleAnimator=ValueAnimator.ofFloat(1f,2f)

        yAnimator.addUpdateListener {
            val value=it.animatedValue as Float
            ImageView.translationY=value

        }
        alphaAnimator.addUpdateListener {
            val value=it.animatedValue as Float
            ImageView.alpha=value
        }
        scaleAnimator.addUpdateListener {
            val value=it.animatedValue as Float
            ImageView.scaleX=value
            ImageView.scaleY=value
        }
        val accelerateInterpolator=AccelerateInterpolator(1.5f)
        alphaAnimator.interpolator=accelerateInterpolator
        yAnimator.interpolator=accelerateInterpolator
        scaleAnimator.interpolator=accelerateInterpolator
        yAnimator.duration=1000
        alphaAnimator.duration=1000
        scaleAnimator.duration=1000
        yAnimator.start()
        alphaAnimator.start()
        scaleAnimator.start()
    }

    private fun startActivity() {

        cookieButton1.isEnabled = true
        cookieButton1.visibility = View.VISIBLE

        cookieButton2.isEnabled = true
        cookieButton2.visibility = View.VISIBLE

    }

    private fun finnishActivity() {

        cookieButton1.isEnabled = false
        cookieButton1.visibility = View.GONE

        cookieButton2.isEnabled = false
        cookieButton2.visibility = View.GONE

        stopChronometer()
        displayGameOverPopup()

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

    private fun modifyChronometerTime(mod: Long) {
        chronometer.base += mod
    }

}