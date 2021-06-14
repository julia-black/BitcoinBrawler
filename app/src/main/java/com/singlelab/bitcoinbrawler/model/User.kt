package com.singlelab.bitcoinbrawler.model

import com.singlelab.bitcoinbrawler.model.exception.BuyingException
import com.singlelab.bitcoinbrawler.model.exception.ErrorType
import com.singlelab.bitcoinbrawler.util.roundTo
import kotlin.math.roundToInt

data class User(
    var amountBtc: Int,
    var amountDollar: Double,
    var velocity: Int,
    var products: MutableList<Product> = mutableListOf()
) {
    fun incBtc(): User {
        amountBtc += getAllVelocity()
        return this
    }

    fun getAllVelocity(): Int {
        var allVelocity = velocity
        products.forEach {
            if (it.velocityIncBoost != null) {
                allVelocity += it.velocityIncBoost
            }
            if (it.velocityMultiplyBoost != null) {
                allVelocity = (allVelocity * it.velocityMultiplyBoost).roundToInt()
            }
        }
        return allVelocity
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

    fun buyProduct(product: Product): User? {
        if (product.price <= amountDollar) {
            amountDollar -= product.price
            products.add(product)
        } else {
            throw BuyingException(ErrorType.NOT_ENOUGH_DOLLARS)
        }
        return this
    }

    fun getPepe(): Product? {
        return products.findLast {
            it.type == ProductType.PEPE
        }
    }

    fun getDrill(): String {
        return products.findLast {
            it.type == ProductType.DRILL
        }?.name ?: ""
    }
}