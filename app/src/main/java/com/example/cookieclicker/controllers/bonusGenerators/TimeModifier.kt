package com.example.cookieclicker.controllers.bonusGenerators

import com.example.cookieclicker.controllers.GameController

class TimeModifier : IBonusGenerator {

    private val _gameController:GameController

    constructor(gameController: GameController){
        this._gameController=gameController
    }

    override val bonus: Float
        get() = 1f
    override val upgradeCost: Float
        get() = _cost

    private val _bonus = 30000L
    private val _cost = 100f

    override fun Upgrade() {
        _gameController.onTimeModified.invoke(_bonus)
    }

    override fun toString(): String {
        return "GIT reset --hard: Go back in time ${_bonus}s  for $_cost"
    }

}