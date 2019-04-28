package com.example.cookieclicker.controllers

import android.app.IntentService
import android.app.Service
import android.content.Context
import android.os.SystemClock
import android.widget.Chronometer
import com.example.cookieclicker.controllers.bonusGenerators.*

//C# Events equivalent
class Event<T> {
    private val handlers = arrayListOf<(Event<T>.(T) -> Unit)>()
    fun plusAssign(handler: Event<T>.(T) -> Unit) {
        handlers.add(handler)
    }

    fun invoke(value: T) {
        for (handler in handlers) handler(value)
    }
}


class GameController(activity: Context, private val chronometer: Chronometer) {

    //GameEvents
    val onGameFinished = Event<String>()
    val onPointsChanged = Event<String>()
    val onTimeModified=Event<Long>()
    //

    //VariableFields
    var score = 0f
    private val scoreLimit = 100
    var username = "Anonymous"
    private var incomeModifier = 1f
    //

    //Here all interactables are gathered
    val list = listOf(
        SimpleClickPointGenerator(),
        ClickHoldPointGenerator(),
        TimeBasedPointGenerator(),
        IncomeModifier(this),
        TimeModifier(this)

    )

    //should i use it?
    private var context: Context = activity

    fun setup(){
        chronometer.setOnChronometerTickListener { grantPoints(list[2]) }
    }
    private fun finnishGame() {
        checkForHighScore(chronometer.base.toInt())
        onGameFinished.invoke("End!")
    }

    private fun checkForHighScore(time:Int) {
        val highscoresController=ReadWriteController(context)
        highscoresController.getHighScores()
        highscoresController.addHighScore(username,time)
    }

    fun changeIncomeModifier(newModifier: Float) {
        incomeModifier = newModifier
    }

    fun grantPoints(generator: IBonusGenerator) {
        score += (generator.GrantPoints()) * incomeModifier

        System.out.println(score)
        onPointsChanged.invoke(score.toString())
        if (score >= scoreLimit) {
            finnishGame()
        }
    }

    fun upgrade(generator: IBonusGenerator):Boolean {
        if (score >= generator.upgradeCost) {
            score -= generator.upgradeCost
            onPointsChanged.invoke(score.toString())
            generator.Upgrade()
            return true
        }
        return false
    }

}