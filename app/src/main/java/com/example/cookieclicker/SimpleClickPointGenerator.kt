package com.example.cookieclicker

class SimpleClickPointGenerator:IBonusGenerator {

    override val upgradeCost: Float
        get() =_costs[currentBonusIndex]

    override val bonus: Float
        get() = _bonuses[currentBonusIndex]

    private val _bonuses= mutableListOf(1f,2f,3f)
    private val _costs= mutableListOf(5f,25f,125f)
    private val _displayStrings= mutableListOf("8","11","12")
    private var currentBonusIndex=0

    override fun Upgrade() {
        if (currentBonusIndex<_bonuses.lastIndex)currentBonusIndex++
    }

    override fun toString(): String {
        if (currentBonusIndex+1>_bonuses.lastIndex) return "Maximum upgrade reached"
        return "Upgrade to Java to Java ${_displayStrings[currentBonusIndex]}: ClickBonus->${_bonuses[currentBonusIndex+1]} for ${_costs[currentBonusIndex+1]}"
    }


}