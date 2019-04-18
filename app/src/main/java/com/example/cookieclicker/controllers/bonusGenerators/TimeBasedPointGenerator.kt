package com.example.cookieclicker

class TimeBasedPointGenerator:IBonusGenerator {

    override val bonus: Float
        get() = _bonuses[currentBonusIndex]

    override val upgradeCost: Float
        get() =_costs[currentBonusIndex]

    private val _bonuses= mutableListOf<Float>(1f,1.5f,2f)
    private val _costs= mutableListOf<Float>(5f,25f,125f)
    private var currentBonusIndex=0;
    private val _displayStrings= mutableListOf("5","6","7")

    override fun Upgrade() {
        if (currentBonusIndex<_bonuses.lastIndex)currentBonusIndex++
    }

    override fun toString(): String {
        if (currentBonusIndex+1>_bonuses.lastIndex) return "Maximum upgrade reached"
        return "Upgrade Angular to Angular ${_displayStrings[currentBonusIndex+1]}: OverTimeBonus->${_bonuses[currentBonusIndex+1]} for ${_costs[currentBonusIndex+1]}"
    }
}