package com.singlelab.bitcoinbrawler.util

import kotlin.math.pow
import kotlin.math.roundToInt

fun Float.roundTo(numFractionDigits: Int): Float {
    val factor = 10.0.pow(numFractionDigits.toDouble())
    return (this * factor).roundToInt() / factor.toFloat()
}