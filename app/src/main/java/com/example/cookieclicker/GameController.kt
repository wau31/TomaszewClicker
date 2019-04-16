package com.example.cookieclicker

import android.app.Activity
import android.content.Context
import java.util.*

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
    //GameEvents to be implemented

    val onGameStarted = Event<String>()
    val onGameFinished = Event<String>()
    val onPointsChanged = Event<String>()
    val onTick = Event<String>()

    //VariableFields
    var score = 0f
    var time = 0;
    val scoreLimit = 1000;
    private val interval = 1000L;
    //

    val list = listOf(SimpleClickPointGenerator(), TimeBasedPointGenerator(), ClickHoldPointGenerator())

    private var ContextActivity:Context

    constructor(activity: Context){

        this.ContextActivity = activity
    }

    fun StartGame() {
        val timer = Timer(true)
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                tick()
            }
        }, 0, interval)
        onGameStarted.invoke("Start!")
    }

    private fun tick() {
        time += 1000
        onTick.invoke(time.toString())
        System.out.println(time)

    }

    private fun FinnishGame() {

        CheckForHighScore()
        onGameFinished.invoke("End!")
    }

    private fun CheckForHighScore() {
        val settings = ContextActivity.getSharedPreferences(ContextActivity.getString(R.string.app_settings_path), Context.MODE_PRIVATE)
        var highscores=settings.getStringSet("HighScores", mutableSetOf())
        for (string in highscores)
        {
            if(string.toInt()>time)
            {

            }

        }
    }

    fun GrantPoints(generator: IPointsGenerator) {

        score += generator.GrantPoints()
        System.out.println(score)
        onPointsChanged.invoke(score.toString())
        if (score >= scoreLimit) {
            FinnishGame()
        }
    }

    fun Upgrade(generator: IPointsGenerator) {
        if (score >= generator.upgradeCost) {
            score -= generator.upgradeCost
            onPointsChanged.invoke(score.toString())
            generator.Upgrade()
            //invoke on points changed
        }
    }

}