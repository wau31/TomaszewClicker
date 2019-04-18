package com.example.cookieclicker

interface IBonusGenerator {

    val bonus:Float
    val upgradeCost:Float

    fun GrantPoints():Float{return 1*bonus}
    fun Upgrade()
}