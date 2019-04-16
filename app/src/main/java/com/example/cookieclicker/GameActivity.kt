package com.example.cookieclicker

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {

    private var controller = GameController(this, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        cookieButton1.setOnClickListener {
            controller.GrantPoints(controller.list[0])
        }

        //cookieButton2.setOnLongClickListener { }

        cookieButton3.setOnClickListener { }

        startButton.setOnClickListener {
            startActivity()
        }

        controller.onPointsChanged.plusAssign { scoreCurrent.text=it }

        //controller.onTick.plusAssign { timeLeft.text = it }//???TODO:FIX error


    }

    fun startActivity() {
        // setContentView(R.layout.activity_game)
        startButton.isEnabled = false;

        cookieButton1.isEnabled = true;
        cookieButton2.isEnabled = true;
        cookieButton3.isEnabled = true;
        controller.StartGame()

    }

    fun FinnishActivity() {
        setContentView(R.layout.activity_game)
        startButton.isEnabled = false;
        cookieButton1.isEnabled = true;
        cookieButton2.isEnabled = true;
        cookieButton3.isEnabled = true;

    }
}