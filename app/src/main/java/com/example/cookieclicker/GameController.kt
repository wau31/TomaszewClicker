package com.example.cookieclicker

import android.content.Context

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

    private var ContextActivity: Context

    constructor(activity: Context) {
        this.ContextActivity = activity
    }

    fun StartGame() {
        onGameStarted.invoke("Start!")
    }


    private fun FinnishGame() {

        CheckForHighScore()
        onGameFinished.invoke("End!")
    }

    private fun CheckForHighScore() {
        val settings = ContextActivity.getSharedPreferences(
            ContextActivity.getString(R.string.app_settings_path),
            Context.MODE_PRIVATE
        )
        var highscores = settings.getStringSet("HighScores", mutableSetOf())
        for (string in highscores) {
            if (string.toInt() > time) {

            }

        }
    }

    fun GrantPoints(generator: IBonusGenerator) {
        score += generator.GrantPoints()
        System.out.println(score)
        onPointsChanged.invoke(score.toString())
        if (score >= scoreLimit) {
            FinnishGame()
        }
    }

    fun Upgrade(generator: IBonusGenerator) {
        if (score >= generator.upgradeCost) {
            score -= generator.upgradeCost
            onPointsChanged.invoke(score.toString())
            generator.Upgrade()

        }
    }

}