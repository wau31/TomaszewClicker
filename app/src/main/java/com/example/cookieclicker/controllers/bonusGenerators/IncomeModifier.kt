package com.example.cookieclicker.controllers.bonusGenerators

import android.R
import com.example.cookieclicker.controllers.GameController
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class IncomeModifier : IBonusGenerator {
    val controller: GameController

    constructor(controller: GameController) {
        this.controller = controller
    }

    override val bonus: Float
        get() = 1f
    override val upgradeCost: Float
        get() = _costs[_currentBonusIndex]

    private val _bonuses = mutableListOf(1.5f, 2f, 5f)
    private val _costs = mutableListOf(10f, 50f, 100f)
    private val _displayStrings = mutableListOf("1", "2", "3")
    private var _currentBonusIndex = 0;

    override fun Upgrade() {
        if (_currentBonusIndex < _bonuses.lastIndex) {
            controller.changeIncomeModifier(_bonuses[_currentBonusIndex++])
            GlobalScope.launch {
                delay(1000)
                controller.changeIncomeModifier(1f)
            }
        }
    }

    override fun toString(): String {
        return "Upgrade to .NET Core ${_displayStrings[_currentBonusIndex]}:Multiplayer->x${_bonuses[_currentBonusIndex]} for ${_costs[_currentBonusIndex]}/10s"
    }
}