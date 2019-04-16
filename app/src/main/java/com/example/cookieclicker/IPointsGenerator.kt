package com.example.cookieclicker

interface IPointsGenerator {

    val bonus:Float
    val upgradeCost:Float

    fun GrantPoints():Float{return 1*bonus}
    fun Upgrade()
}