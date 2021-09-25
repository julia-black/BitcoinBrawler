package com.singlelab.bitcoinbrawler.model

import com.singlelab.bitcoinbrawler.model.exception.BuyingException
import com.singlelab.bitcoinbrawler.model.exception.ErrorType
import com.singlelab.bitcoinbrawler.util.roundTo
import kotlin.math.roundToInt

data class User(
    var amountBtc: Float,
    var amountDollar: Float,
    var velocity: Float,
    var products: MutableList<Product> = mutableListOf()
) {

    val productIds
        get() = products.joinToString(",")

    fun incBtc(): User {
        amountBtc += getAllVelocity()
        return this
    }

    fun getAllVelocity(): Float {
        var allVelocity = velocity
        products.forEach {
            if (it.velocityIncBoost != null) {
                allVelocity += it.velocityIncBoost
            }
            if (it.velocityMultiplyBoost != null) {
                allVelocity = (allVelocity * it.velocityMultiplyBoost).toFloat()
            }
        }
        return allVelocity
    }

    fun buyBtc(amount: Float, price: Float): User {
        if (amountDollar >= amount * price) {
            amountDollar -= amount * price
            amountDollar = amountDollar.roundTo(2)
            amountBtc += amount
        } else {
            throw BuyingException(ErrorType.NOT_ENOUGH_DOLLARS)
        }
        return this
    }

    fun sellBtc(amount: Float, price: Float): User {
        if (amountBtc >= amount) {
            amountBtc -= amount
            amountDollar += amount * price
            amountDollar = amountDollar.roundTo(2)
        } else {
            throw BuyingException(ErrorType.NOT_ENOUGH_BTC)
        }
        return this
    }

    fun buyProduct(product: Product): User {
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

    fun getDrill(): Product? {
        return products.findLast {
            it.type == ProductType.DRILL
        }
    }

    fun getOtherProducts(): List<Product> {
        return products.filter {
            it.type == ProductType.OTHER
        }
    }
}