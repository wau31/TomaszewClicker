package com.example.cookieclicker

class TimeBasedPointGenerator:IPointsGenerator {

    override val bonus: Float
        get() = _bonuses[currentBonusIndex]

    override val upgradeCost: Float
        get() =_costs[currentBonusIndex]

    private val _bonuses= mutableListOf<Float>(1f,1.5f,2f)
    private val _costs= mutableListOf<Float>(5f,25f,125f)
    private var currentBonusIndex=0;

    override fun Upgrade() {
        if (currentBonusIndex<_bonuses.lastIndex)currentBonusIndex++
    }
}