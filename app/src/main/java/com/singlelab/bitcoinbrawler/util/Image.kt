package com.singlelab.bitcoinbrawler.util

import android.content.Context
import com.singlelab.bitcoinbrawler.R
import com.singlelab.bitcoinbrawler.model.Const
import com.singlelab.bitcoinbrawler.model.Product

fun Context.getDrawableByProduct(product: Product) = getDrawable(product.getDrawableRes())

fun Product.getDrawableRes(): Int {
    return when (id) {
        Const.PEPE_ID_1 -> R.mipmap.pepe_default
        Const.PEPE_ID_2 -> R.mipmap.pepehappy
        Const.PEPE_ID_3 -> R.mipmap.pepe_default
        Const.PEPE_ID_4 -> R.mipmap.pepemad
        Const.PEPE_ID_5 -> R.mipmap.pepeveryhappy
        Const.DRILL_ID_1 -> R.mipmap.drill_default
        Const.DRILL_ID_2 -> R.mipmap.drill_default
        Const.DRILL_ID_3 -> R.mipmap.drill_default
        else -> throw Exception("Not found drawable with product id = $id")
    }
}