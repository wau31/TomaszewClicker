package com.example.cookieclicker.controllers


import android.content.Context
import com.example.cookieclicker.models.HighScore
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream
import java.io.OutputStreamWriter
import java.lang.Exception

class ReadWriteController {

    private var highScores = arrayListOf<HighScore>()

    private var contextActivity: Context

    constructor(activity: Context) {
        this.contextActivity = activity
    }

    fun getHighScores(): ArrayList<HighScore> {
        if (highScores.isNullOrEmpty()) {
            readFile()
        }
        return highScores
    }

    fun resetHighScores(){
        highScores.clear()
        writeFile()
    }

    fun addHighScore(name: String, score: Int) {
        highScores.add(HighScore(name, score))
        highScores.sortBy { score}
        writeFile()
    }

    private fun readFile() {
        var json: String? = null

        try {

            //val inputStream: InputStream = contextActivity.assets.open("TomaszewClickerConfig.json")
            val s=contextActivity.filesDir
            val inputStream: InputStream = contextActivity.openFileInput("TomaszewClickerConfig.json")

            json = inputStream.bufferedReader().use { it.readText() }
            var jsonArr = JSONObject(json).getJSONArray("Highscores")
            for (i in 0 until jsonArr.length()) {
                var jsonObject = jsonArr.getJSONObject(i)
                highScores.add(HighScore(jsonObject.getString("name"), jsonObject.getInt("score")))
            }
        } catch (e: Exception) {
            System.out.println(e.message)
        }

    }

    private fun writeFile() {

        var newHighScores = JSONArray()
        for (i in 0 until highScores.count()) {
            var jsonobject = JSONObject().put("name", highScores[i].name).put("score", highScores[i].score)
            newHighScores.put(jsonobject)
        }
        var obj=JSONObject()
        obj.put("Highscores",newHighScores)
        val writer=OutputStreamWriter(contextActivity.openFileOutput("TomaszewClickerConfig.json", Context.MODE_PRIVATE))
        writer.write(obj.toString())
        writer.close()


    }


}