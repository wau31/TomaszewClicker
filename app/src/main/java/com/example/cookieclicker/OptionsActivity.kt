package com.example.cookieclicker

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_options.*

class OptionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_options)
        switch1.setOnCheckedChangeListener { buttonView, isChecked ->

                val settings = getSharedPreferences(getString(R.string.app_settings_path),Context.MODE_PRIVATE)
                var editor=settings.edit()
                if(isChecked){
                    editor.putBoolean("RunInBackground",true)
                }
                else{
                    editor.putBoolean("RunInBackground",false)
                }
                editor.commit()
        }
    }


}
