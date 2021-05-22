package com.singlelab.bitcoinbrawler.model

import com.singlelab.bitcoinbrawler.model.exception.BuyingException
import com.singlelab.bitcoinbrawler.model.exception.ErrorType
import com.singlelab.bitcoinbrawler.util.roundTo

data class User(
    var amountBtc: Int,
    var amountDollar: Double,
    var velocity: Int
) {
    fun incBtc(): User {
        amountBtc += velocity
        return this
    }

    fun buyBtc(amount: Int, price: Double): User {
        if (amountDollar >= amount * price) {
            amountDollar -= amount * price
            amountDollar = amountDollar.roundTo(2)
            amountBtc += amount
        } else {
            throw BuyingException(ErrorType.NOT_ENOUGH_DOLLARS)
        }
        return this
    }

    fun sellBtc(amount: Int, price: Double): User {
        if (amountBtc >= amount) {
            amountBtc -= amount
            amountDollar += amount * price
            amountDollar = amountDollar.roundTo(2)
        } else {
            throw BuyingException(ErrorType.NOT_ENOUGH_BTC)
        }
        return this
    }
}