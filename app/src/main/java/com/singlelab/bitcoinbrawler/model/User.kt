package com.singlelab.bitcoinbrawler.model

data class User(
    var amountBtc: Int,
    var amountDollar: Int,
    var velocity: Int
) {
    fun incBtc(): User {
        amountBtc += velocity
        return this
    }
}