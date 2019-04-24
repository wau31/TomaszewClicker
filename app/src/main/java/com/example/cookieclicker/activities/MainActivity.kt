package com.example.cookieclicker.activities

import android.app.ActivityOptions
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import com.example.cookieclicker.R
import com.example.cookieclicker.controllers.ReadWriteController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val config = "TomaszewClickerConfig"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PreferenceManager.getDefaultSharedPreferences(this).edit().putString("path", config).apply()

        NewGameButton.setOnClickListener {
            startActivity(
                Intent(this, GameActivity::class.java),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            )
        }
        OptionsButton.setOnClickListener {
            startActivity(
                Intent(this, OptionsActivity::class.java),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            )
        }
        RankingButton.setOnClickListener {
            startActivity(
                Intent(this, RankingActivity::class.java),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            )
        }
        ExitButton.setOnClickListener { }
    }


}
