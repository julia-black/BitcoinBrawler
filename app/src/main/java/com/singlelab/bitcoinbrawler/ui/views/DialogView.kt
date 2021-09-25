package com.singlelab.bitcoinbrawler.ui.views


import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.singlelab.bitcoinbrawler.R


class DialogView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var buttonClose: View? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_dialog, this, true)
        buttonClose = findViewById(R.id.button_close)
    }

    fun setDialogListener(onClick: () -> Unit) {
        LayoutInflater.from(context).inflate(R.layout.view_button, this, true)
        buttonClose?.setOnClickListener {
            onClick.invoke()
        }
    }
}