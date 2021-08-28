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
    PEPE_1(
        Const.PEPE_ID_1,
        Res.getStr(R.string.pepe_1),
        0,
        ProductType.PEPE,
        Res.getStr(R.string.pepe_1_desc),
        0
    ),
    PEPE_2(
        Const.PEPE_ID_2,
        Res.getStr(R.string.pepe_2),
        200,
        ProductType.PEPE,
        Res.getStr(R.string.pepe_2_desc),
        2
    ),
    PEPE_3(
        Const.PEPE_ID_3,
        Res.getStr(R.string.pepe_3),
        800,
        ProductType.PEPE,
        Res.getStr(R.string.pepe_3_desc),
        4
    ),
    PEPE_4(
        Const.PEPE_ID_4,
        Res.getStr(R.string.pepe_4),
        1600,
        ProductType.PEPE,
        Res.getStr(R.string.pepe_4_desc),
        8
    ),
    PEPE_5(
        Const.PEPE_ID_5,
        Res.getStr(R.string.pepe_5),
        10000,
        ProductType.PEPE,
        Res.getStr(R.string.pepe_5_desc),
        16
    ),

    DRILL_1(
        Const.DRILL_ID_1,
        Res.getStr(R.string.drill_1),
        0,
        ProductType.DRILL,
        Res.getStr(R.string.drill_1_desc),
        null,
        1.0
    ),
    DRILL_2(
        Const.DRILL_ID_2,
        Res.getStr(R.string.drill_2),
        10000,
        ProductType.DRILL,
        Res.getStr(R.string.drill_2_desc),
        null,
        2.0
    ),
    DRILL_3(
        Const.DRILL_ID_3,
        Res.getStr(R.string.drill_3),
        40000,
        ProductType.DRILL,
        Res.getStr(R.string.drill_3_desc),
        null,
        4.00
    ),

    MONSTER(
        Const.MONSTER_ID,
        Res.getStr(R.string.monster_title),
        100,
        ProductType.OTHER,
        Res.getStr(R.string.monster_desc),
        null,
        1.2
    ),

    SHLEPPA(
        Const.SHLEPPA_ID,
        Res.getStr(R.string.shleppa_title),
        10000,
        ProductType.OTHER,
        Res.getStr(R.string.shleppa_desc),
        null,
        5.0
    ),

    GUCCI(
        Const.GUCCI_ID,
        Res.getStr(R.string.gucci_title),
        500,
        ProductType.OTHER,
        Res.getStr(R.string.gucci_desc),
        null,
        1.5
    ),

    SPIDY(
        Const.SPIDY_ID,
        Res.getStr(R.string.spidy_title),
        5000,
        ProductType.OTHER,
        Res.getStr(R.string.spidy_desc),
        null,
        3.0
    ),

    SHREK(
        Const.SHREK_ID,
        Res.getStr(R.string.shrek_title),
        2000,
        ProductType.OTHER,
        Res.getStr(R.string.shrek_desc),
        null,
        2.0
    ),

    DOGE(
        Const.DOGE_ID,
        Res.getStr(R.string.doge_title),
        4000,
        ProductType.OTHER,
        Res.getStr(R.string.doge_desc),
        null,
        4.0
    )
}