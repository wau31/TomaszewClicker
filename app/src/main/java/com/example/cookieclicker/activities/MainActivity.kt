package com.example.cookieclicker

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NewGameButton.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
        }
        OptionsButton.setOnClickListener {
            startActivity(Intent(this, OptionsActivity::class.java))
        }
        RankingButton.setOnClickListener {
            startActivity(Intent(this, RankingActivity::class.java))
        }
        ExitButton.setOnClickListener { }
    }


}
