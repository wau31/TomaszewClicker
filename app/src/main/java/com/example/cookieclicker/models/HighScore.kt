package com.example.cookieclicker.models

class HighScore(name: String, score: Int) {
    val name: String = name
    val score: Int = score
    override fun toString(): String {
        return "$name: $score"
    }
}