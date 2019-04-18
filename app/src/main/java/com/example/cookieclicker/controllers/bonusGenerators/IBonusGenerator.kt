package com.example.cookieclicker.controllers.bonusGenerators

interface IBonusGenerator {

    val bonus:Float
    val upgradeCost:Float

    fun GrantPoints():Float{return 1*bonus}
    fun Upgrade()
}