package com.example.cookieclicker.controllers

import android.content.Context
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


class GameController {

    //GameEvents
    val onGameFinished = Event<String>()
    val onPointsChanged = Event<String>()
    val onTimeModified=Event<Long>()
    //

    //VariableFields
    var score = 0f
    private val scoreLimit = 1000;
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
    private var ContextActivity: Context

    constructor(activity: Context) {
        this.ContextActivity = activity
    }


    private fun finnishGame() {
        checkForHighScore()
        onGameFinished.invoke("End!")
    }

    fun checkForHighScore() {

    }

    fun changeIncomeModifier(newModifier: Float) {
        incomeModifier = newModifier;
    }

    fun grantPoints(generator: IBonusGenerator) {
        score += (generator.GrantPoints()) * incomeModifier

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