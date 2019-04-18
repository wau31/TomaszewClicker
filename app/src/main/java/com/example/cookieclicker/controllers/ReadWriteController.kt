package com.example.cookieclicker.controllers

import android.content.Context
import com.example.cookieclicker.models.HighScore
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.lang.Exception

class ReadWriteController {
    private var ContextActivity:Context

    private var highScores= arrayListOf<HighScore>()
    get() = this.highScores

    constructor(activity:Context){
        ContextActivity=activity
    }

    private fun readFile(){
        var json:String?=null

        try {
            val inputStream: InputStream = ContextActivity.assets.open("TomaszewClickerConfig.json")
            json = inputStream.bufferedReader().use { it.readText() }
            var jsonArr= JSONObject(json).getJSONArray("Highscores")
            for(i in 0 until jsonArr.length())
            {
                var jsonObject=jsonArr.getJSONObject(i)
                highScores.add(HighScore(jsonObject.getString("name"),jsonObject.getInt("score")))
            }
        }
        catch (e: Exception)
        {

        }
    }

    fun addHighScore(name:String,score:Int){
        highScores.add(HighScore(name,score))
        writeFile()
    }

    private fun writeFile(){
        var newHighScore=JSONArray()
        for (i in 0 until highScores.count()){
            var jsonobject=JSONObject().put("name",highScores[i].name).put("score",highScores[i].score)
            newHighScore.put(jsonobject)
        }

        ContextActivity.openFileOutput("TomaszewClickerConfig.json",Context.MODE_PRIVATE).use{
            it.write(newHighScore.toString().toByteArray())
        }

    }
}