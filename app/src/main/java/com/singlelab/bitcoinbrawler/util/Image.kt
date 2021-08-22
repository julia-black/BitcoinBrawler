package com.singlelab.bitcoinbrawler.util

import android.content.Context
import com.singlelab.bitcoinbrawler.R
import com.singlelab.bitcoinbrawler.model.Product

fun Context.getDrawableByProduct(product: Product) = getDrawable(product.getDrawableRes())

fun Product.getDrawableRes(): Int {
    return when (this) {
        Product.PEPE_1 -> R.mipmap.pepe_default
        Product.PEPE_2 -> R.mipmap.pepehappy
        Product.PEPE_3 -> R.mipmap.normis
        Product.PEPE_4 -> R.mipmap.pepemad
        Product.PEPE_5 -> R.mipmap.pepeveryhappy
        Product.DRILL_1 -> R.mipmap.drill_default
        Product.DRILL_2 -> R.mipmap.superdrill
        Product.DRILL_3 -> R.mipmap.superdill2
        Product.MONSTER -> R.mipmap.monster
        Product.SHLEPPA -> R.mipmap.shleppa
        Product.GUCCI -> R.mipmap.gucci
        Product.DOGE -> R.mipmap.doge
        Product.SHREK -> R.mipmap.shrek
        Product.SPIDY -> R.mipmap.spidy
        else -> throw Exception("Not found drawable with product id = $id")
    }
}