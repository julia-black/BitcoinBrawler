package com.singlelab.bitcoinbrawler.ui.stock

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.singlelab.bitcoinbrawler.MainActivity
import com.singlelab.bitcoinbrawler.R
import com.singlelab.bitcoinbrawler.databinding.FragmentStockBinding
import com.singlelab.bitcoinbrawler.model.exception.BuyingException
import com.singlelab.bitcoinbrawler.ui.base.BaseFragment
import com.singlelab.bitcoinbrawler.util.roundTo

class StockFragment : BaseFragment() {

    private lateinit var stockViewModel: StockViewModel
    private var _binding: FragmentStockBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        stockViewModel =
            ViewModelProvider(this).get(StockViewModel::class.java)

        _binding = FragmentStockBinding.inflate(inflater, container, false)
        val root: View = binding.root

        observeViewModel()

        showInfoAmount()

        setListeners()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
        (activity as MainActivity).pricesLiveData.observe(viewLifecycleOwner, {
            var prices = ""
            it.forEach { price ->
                prices = "$prices $price"
            }
            binding.textDashboard.text = prices
            try {
                showInfoAmount(parseAmount(binding.amount.text))
            } catch (e: Exception) {
                showInfoAmount()
            }
        })

        stockViewModel.userLiveData.observe(viewLifecycleOwner, {
            if (it != null) {
                (activity as MainActivity).userLiveData.value = it
                showToast(getString(R.string.success))
                binding.amount.setText("")
            }
        })
    }

    private fun getActualPrice(): Double? {
        return (activity as MainActivity).pricesLiveData.value?.last()
    }

    private fun showInfoAmount(selectedAmount: Int = 1) {
        val price = getActualPrice()
        if (price != null) {
            binding.infoAmount.text =
                "$selectedAmount BTC = ${(price * selectedAmount).roundTo(2)}$"
        }
    }

    private fun setListeners() {
        with(binding) {
            amount.addTextChangedListener {
                try {
                    showInfoAmount(parseAmount(it))
                } catch (e: Exception) {
                    showInfoAmount()
                }
            }
            buttonBuyBtc.setOnClickListener {
                try {
                    stockViewModel.buyBtc(
                        getAmount(),
                        (activity as MainActivity).userLiveData.value,
                        getActualPrice()
                    )
                } catch (e: BuyingException) {
                    showError(e)
                }
            }
            buttonSellBtc.setOnClickListener {
                try {
                    stockViewModel.sellBtc(
                        getAmount(),
                        (activity as MainActivity).userLiveData.value,
                        getActualPrice()
                    )
                } catch (e: BuyingException) {
                    showError(e)
                }
            }
        }
    }

    private fun getAmount(): Int? {
        try {
            return parseAmount(binding.amount.text)
        } catch (e: Exception) {
            showEmptyAmountError()
        }
        return null
    }

    private fun parseAmount(text: Editable?): Int {
        if (text.toString().isNotEmpty()) {
            return text.toString().toInt()
        } else {
            throw Exception()
        }
    }

    private fun showEmptyAmountError() {
        showToast(getString(R.string.amount_error))
    }
}