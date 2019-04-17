package com.example.cookieclicker

class ClickHoldPointGenerator:IBonusGenerator {

    override val bonus: Float
        get() = _bonuses[currentBonusIndex]

    override val upgradeCost: Float
        get() =_costs[currentBonusIndex]
    private val _bonuses= mutableListOf(2f,3f,4f)
    private val _costs= mutableListOf(5f,25f,125f)
    private val _displayStrings= mutableListOf("2","3","4")
    private var currentBonusIndex=0;

    override fun Upgrade() {
        if (currentBonusIndex<_bonuses.lastIndex)currentBonusIndex++
    }

    override fun toString(): String {
        if (currentBonusIndex+1>_bonuses.lastIndex) return "Maximum upgrade reached"
        return "Upgrade .NET to .NET ${_displayStrings[currentBonusIndex+1]}: LongClickBonus->${_bonuses[currentBonusIndex+1]} for ${_costs[currentBonusIndex+1]}"
    }
}