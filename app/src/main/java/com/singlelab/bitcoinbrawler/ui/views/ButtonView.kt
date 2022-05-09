package com.singlelab.bitcoinbrawler.ui.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.singlelab.bitcoinbrawler.R


@SuppressLint("ClickableViewAccessibility")
class ButtonView : FrameLayout {
    private var layout: ConstraintLayout? = null
    private var textView: TextView? = null

    constructor(context: Context) : super(context) {
        customInit(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        customInit(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        customInit(context)
    }

    init {
        setOnTouchListener { _, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                layout?.setBackgroundResource(R.mipmap.button_clicked)
            } else {
                layout?.setBackgroundResource(R.mipmap.button_active)
            }
            return@setOnTouchListener false
        }
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        layout?.setBackgroundResource(if (enabled) R.mipmap.button_active else R.mipmap.button_inactive)
    }

    private fun customInit(ctx: Context) {
        LayoutInflater.from(ctx).inflate(R.layout.view_button, this, true)
        layout = findViewById<View>(R.id.buttonLayout) as ConstraintLayout
        textView = findViewById<View>(R.id.buttonText) as TextView
    }
}