package com.singlelab.bitcoinbrawler.ui.base

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.singlelab.bitcoinbrawler.R
import com.singlelab.bitcoinbrawler.model.exception.BuyingException
import com.singlelab.bitcoinbrawler.model.exception.ErrorType

abstract class BaseFragment : Fragment() {
    protected fun showError(e: BuyingException) {
        showToast(
            when (e.errorType) {
                ErrorType.NOT_ENOUGH_BTC -> getString(R.string.not_enough_btc)
                ErrorType.NOT_ENOUGH_DOLLARS -> getString(R.string.not_enough_dollars)
            }
        )
    }

    protected fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}