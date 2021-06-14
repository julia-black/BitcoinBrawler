package com.singlelab.bitcoinbrawler.model

data class Product(
    val id: Int,
    val name: String,
    val price: Int,
    val type: ProductType,
    val description: String,
    val velocityIncBoost: Int? = null,
    val velocityMultiplyBoost: Double? = null
)