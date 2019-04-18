package com.example.cookieclicker.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import com.example.cookieclicker.R
import com.example.cookieclicker.controllers.ReadWriteController
import kotlinx.android.synthetic.main.activity_ranking.*

class RankingActivity: AppCompatActivity(){
    val ReadWriteController= ReadWriteController(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)
        var adapter=ArrayAdapter(this,android.R.layout.simple_list_item_2,ReadWriteController.)
        highscore_list.
    }
}