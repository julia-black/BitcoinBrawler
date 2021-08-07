package com.singlelab.bitcoinbrawler.ui.stock

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.singlelab.bitcoinbrawler.MainActivity
import com.singlelab.bitcoinbrawler.R
import com.singlelab.bitcoinbrawler.databinding.FragmentStockBinding
import com.singlelab.bitcoinbrawler.model.exception.BuyingException
import com.singlelab.bitcoinbrawler.ui.base.BaseFragment
import com.singlelab.bitcoinbrawler.util.roundTo
import java.util.*

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

    private fun initChart() {
        with(binding.chart) {
            setBackgroundColor(Color.WHITE)
            description.isEnabled = false
            setDrawGridBackground(false)
            isSelected = false
            isDragEnabled = true
            setScaleEnabled(true)
            setPinchZoom(true)
            xAxis.setDrawLabels(false)
        }
    }

    private fun setData(values: List<Entry>) {
        with(binding.chart) {
            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(
                LineDataSet(values, getString(R.string.price_btc)).apply {
                    axisDependency = AxisDependency.LEFT
                    color = ContextCompat.getColor(context, R.color.background)
                    setCircleColor(Color.WHITE)
                    lineWidth = 2f
                    circleRadius = 3f
                    fillAlpha = 65
                    highLightColor = ContextCompat.getColor(context, R.color.black)
                    setCircleColor(ContextCompat.getColor(context, R.color.background_dark))
                    setDrawCircles(true)
                    setDrawCircleHole(false)
                })
            this.data = LineData(dataSets)
        }
    }

    private fun observeViewModel() {
        (activity as MainActivity).pricesLiveData.observe(viewLifecycleOwner, {
            var prices = ""
            it.forEach { price ->
                prices = "$prices $price"
            }

            val entries = it.mapIndexed { index, value ->
                Entry(index.toFloat(), value.toFloat())
            }
            binding.chart.clear()
            initChart()
            setData(entries)
            try {
                showInfoAmount(parseAmount(binding.amount.text))
            } catch (e: Exception) {
                showInfoAmount()
            }
        })

        stockViewModel.userLiveData.observe(viewLifecycleOwner, {
            with(activity as MainActivity) {
                if (it != null) {
                    preference.updateUserData(it)
                    userLiveData.value = it
                    showToast(getString(R.string.success))
                    binding.amount.setText("")
                }
            }
        })
    }

    private fun getActualPrice(): Float? {
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