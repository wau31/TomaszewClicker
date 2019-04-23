package com.example.cookieclicker.controllers.bonusGenerators

class TimeBasedPointGenerator: IBonusGenerator {

    override val bonus: Float
        get() =  checkBonus()

    override val upgradeCost: Float
        get() =_costs[_currentBonusIndex]

    private val _bonuses= mutableListOf<Float>(1f,1.5f,2f)
    private val _costs= mutableListOf<Float>(5f,25f,125f)
    private var _currentBonusIndex=0;
    private val _displayStrings= mutableListOf("5","6","7")
    var isEnabled=false;

    private fun checkBonus():Float{
        return if (!isEnabled) 0f
        else _bonuses[_currentBonusIndex]
    }
    override fun Upgrade() {
        if (_currentBonusIndex<_bonuses.lastIndex){
            if (!isEnabled)isEnabled=true
            _currentBonusIndex++}
    }

    override fun toString(): String {
        if (_currentBonusIndex+1>_bonuses.lastIndex) return "Maximum upgrade reached"
        return "Upgrade to Angular ${_displayStrings[_currentBonusIndex]}: OverTimeBonus->${_bonuses[_currentBonusIndex]} for ${_costs[_currentBonusIndex]}"
    }
}