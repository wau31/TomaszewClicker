package com.example.cookieclicker.activities

import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import com.example.cookieclicker.R
import kotlinx.android.synthetic.main.activity_options.*


class OptionsActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        switch1.isChecked=preferences.getBoolean("RunInBackground",false)

        switch1.setOnCheckedChangeListener { _, isChecked ->
            val editor = preferences.edit()
            if (isChecked) {
                editor.putBoolean("RunInBackground", true)
            } else {
                editor.putBoolean("RunInBackground", false)
            }
            editor.apply()
        }
    }


}
