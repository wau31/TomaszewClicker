package com.example.cookieclicker.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.SystemClock
import android.preference.PreferenceManager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.*
import com.example.cookieclicker.controllers.GameController
import com.example.cookieclicker.R
import com.example.cookieclicker.controllers.bonusGenerators.IBonusGenerator
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.dialog_username.*

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

        setupChronometer()
        //setupUpgradesList()
        val popup = setupUpgradeListV2()
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

    private fun setupUpgradesList() {
        spinner = findViewById(R.id.spinner)
        val adapter = ArrayAdapter(
            this,
            R.layout.support_simple_spinner_dropdown_item, controller.list
        )
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = parent?.getItemAtPosition(position)
                if (item is IBonusGenerator) {
                    controller.upgrade(item)
                }
            }
        }
    }

    private fun setupUpgradeListV2(): PopupWindow {
        val popupWindow = PopupWindow(this)
        val listView = ListView(this)
        listView.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, controller.list)
        listView.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                val fadeInAnimation = AnimationUtils.loadAnimation(view.context, android.R.anim.fade_in)
                fadeInAnimation.duration = 10
                popupWindow.dismiss()
                val item = parent?.getItemAtPosition(position)
                if (item is IBonusGenerator) {
                    controller.upgrade(item)
                }
            }


        }
        popupWindow.isFocusable = true
        popupWindow.width
        popupWindow.height = WindowManager.LayoutParams.WRAP_CONTENT
        popupWindow.contentView = listView
        return popupWindow

    }

    private fun setupChronometer() {
        chronometer = findViewById(R.id.chronometer)
        chronometer.setOnChronometerTickListener { controller.grantPoints(controller.list[2]) }
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
                finnishActivity()
            }
            startActivity()
            alertDialog.dismiss()
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

    private fun handleAnimation(ImageView: ImageView) {
        ImageView.clearAnimation()
        ImageView.animate().translationYBy(-300f).scaleX(1.2f).scaleY(1.2f).alpha(0f).duration = 500

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