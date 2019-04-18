package com.example.cookieclicker.controllers

import android.content.Context
import com.example.cookieclicker.controllers.bonusGenerators.ClickHoldPointGenerator
import com.example.cookieclicker.controllers.bonusGenerators.IBonusGenerator
import com.example.cookieclicker.controllers.bonusGenerators.SimpleClickPointGenerator
import com.example.cookieclicker.controllers.bonusGenerators.TimeBasedPointGenerator
import com.example.cookieclicker.models.HighScore
import org.json.JSONObject
import java.io.InputStream
import java.lang.Exception

class Event<T> {
    private val handlers = arrayListOf<(Event<T>.(T) -> Unit)>()
    fun plusAssign(handler: Event<T>.(T) -> Unit) {
        handlers.add(handler)
    }

    fun invoke(value: T) {
        for (handler in handlers) handler(value)
    }
}


class GameController {
    var highScores= arrayListOf<HighScore>()
    //GameEvents to be implemented
    val onGameFinished = Event<String>()
    val onPointsChanged = Event<String>()

    //VariableFields
    var score = 0f
    private val scoreLimit = 1000;

    //

    val list = listOf(
        SimpleClickPointGenerator(),
        TimeBasedPointGenerator(),
        ClickHoldPointGenerator()
    )

    //should i use it?
    private var ContextActivity: Context

    constructor(activity: Context) {
        this.ContextActivity = activity
    }


    private fun finnishGame() {
        checkForHighScore()
        onGameFinished.invoke("End!")
    }

    private fun ReadFile(){
        var json:String?=null
        try {
            val inputStream: InputStream = ContextActivity.assets.open("TomaszewClickerConfig.json")
            json = inputStream.bufferedReader().use { it.readText() }
            var jsonArr=JSONObject(json).getJSONArray("Highscores")
            for(i in 0 until jsonArr.length())
            {
                var jsonObject=jsonArr.getJSONObject(i)
                highScores.add(HighScore(jsonObject.getString("name"),jsonObject.getInt("score")))
            }
        }
        catch (e:Exception)
        {

        }
    }
    private fun WriteFile(){

    }

    fun checkForHighScore() {
        ReadFile()
        highScores.add(HighScore())

    }

    fun grantPoints(generator: IBonusGenerator) {
        score += generator.GrantPoints()
        System.out.println(score)
        onPointsChanged.invoke(score.toString())
        if (score >= scoreLimit) {
            finnishGame()
        }
    }

    fun upgrade(generator: IBonusGenerator) {
        if (score >= generator.upgradeCost) {
            score -= generator.upgradeCost
            onPointsChanged.invoke(score.toString())
            generator.Upgrade()

        }
    }

}