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
    PEPE_1(Const.PEPE_ID_1, Res.getStr(R.string.pepe_1), 100, ProductType.PEPE, Res.getStr(R.string.pepe_1_desc), 0),
    PEPE_2(Const.PEPE_ID_2, Res.getStr(R.string.pepe_2), 200, ProductType.PEPE, Res.getStr(R.string.pepe_2_desc), 4),
    PEPE_3(Const.PEPE_ID_3, Res.getStr(R.string.pepe_3), 300, ProductType.PEPE, Res.getStr(R.string.pepe_3_desc), 8),
    PEPE_4(Const.PEPE_ID_4, Res.getStr(R.string.pepe_4), 400, ProductType.PEPE, Res.getStr(R.string.pepe_4_desc), 16),
    PEPE_5(Const.PEPE_ID_5, Res.getStr(R.string.pepe_5), 500, ProductType.PEPE, Res.getStr(R.string.pepe_5_desc), 32),

    DRILL_1(Const.DRILL_ID_1,  Res.getStr(R.string.drill_1), 50, ProductType.DRILL, Res.getStr(R.string.drill_1_desc), null, 1.0),
    DRILL_2(Const.DRILL_ID_2,  Res.getStr(R.string.drill_2), 300, ProductType.DRILL, Res.getStr(R.string.drill_2_desc), null, 7.0),
    DRILL_3(Const.DRILL_ID_3,  Res.getStr(R.string.drill_3), 1000, ProductType.DRILL, Res.getStr(R.string.drill_3_desc), null, 11.0),

    MONSTER(
        Const.MONSTER_ID,
        Res.getStr(R.string.monster_title),
        100,
        ProductType.OTHER,
        Res.getStr(R.string.monster_description),
        null,
        1.5
    ),

    SHLEPPA(
        Const.SHLEPPA_ID,
        Res.getStr(R.string.shleppa_title),
        1000,
        ProductType.OTHER,
        Res.getStr(R.string.shleppa_description),
        null,
        3.0
    )
}