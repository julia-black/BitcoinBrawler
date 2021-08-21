package com.singlelab.bitcoinbrawler.data

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("StaticFieldLeak")
object Res {

    private var context: Context? = null

    fun setContext(context: Context?) {
        this.context = context
    }

    fun getStr(id: Int): String = context?.getString(id) ?: ""
}