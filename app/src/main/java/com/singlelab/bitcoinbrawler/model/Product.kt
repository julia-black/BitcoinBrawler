package com.singlelab.bitcoinbrawler.model


import com.singlelab.bitcoinbrawler.R
import com.singlelab.bitcoinbrawler.data.Res

enum class Product(
    val id: Int,
    val title: String,
    val price: Int,
    val type: ProductType,
    val description: String,
    val velocityIncBoost: Int? = null,
    val velocityMultiplyBoost: Double? = null
) {
    PEPE_1(Const.PEPE_ID_1, "Pepe 1", 100, ProductType.PEPE, "Это пепе 1", 0),
    PEPE_2(Const.PEPE_ID_2, "Pepe 2", 200, ProductType.PEPE, "Это пепе 2", 4),
    PEPE_3(Const.PEPE_ID_3, "Pepe 3", 300, ProductType.PEPE, "Это пепе 3", 8),
    PEPE_4(Const.PEPE_ID_4, "Pepe 4", 400, ProductType.PEPE, "Это пепе 4", 16),
    PEPE_5(Const.PEPE_ID_5, "Pepe 5", 500, ProductType.PEPE, "Это пепе 5", 32),

    DRILL_1(Const.DRILL_ID_1, "Drill 1", 50, ProductType.DRILL, "Это дрель 1", null, 1.0),
    DRILL_2(Const.DRILL_ID_2, "Drill 2", 300, ProductType.DRILL, "Это дрель 2", null, 7.0),
    DRILL_3(Const.DRILL_ID_3, "Drill 3", 1000, ProductType.DRILL, "Это дрель 3", null, 11.0),

    MONSTER(
        Const.MONSTER_ID,
        Res.getStr(R.string.monster_title),
        100,
        ProductType.OTHER,
        Res.getStr(R.string.monster_description),
        null,
        1.5
    )
}