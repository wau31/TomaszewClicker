package com.example.cookieclicker

class SimpleClickPointGenerator:IPointsGenerator {
    override val upgradeCost: Float
        get() =_costs[currentBonusIndex]

    override val bonus: Float
        get() = _bonuses[currentBonusIndex]

    private val _bonuses= mutableListOf<Float>(1f,2f,3f)
    private val _costs= mutableListOf<Float>(5f,25f,125f)
    private var currentBonusIndex=0;

    override fun Upgrade() {
        if (currentBonusIndex<_bonuses.lastIndex)currentBonusIndex++
    }


}