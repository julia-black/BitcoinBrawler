package com.singlelab.bitcoinbrawler.ui.stock

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
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
                Entry(index.toFloat(), value)
            }
            binding.chart.clear()
            initChart()
            setData(entries)
            showInfoAmount()
        })

        (activity as MainActivity).isPositiveTrendLiveData.observe(viewLifecycleOwner, {
            updateArrow(it)
        })

        stockViewModel.userLiveData.observe(viewLifecycleOwner, {
            with(activity as MainActivity) {
                if (it != null) {
                    preference.updateUserData(it)
                    userLiveData.value = it
                    showToast(getString(R.string.success))
                }
            }
        })
    }

    private fun getActualPrice(): Float? {
        return (activity as MainActivity).pricesLiveData.value?.last()
    }

    private fun showInfoAmount(view: TextView = binding.infoAmount, selectedAmount: Float = 1f) {
        val price = getActualPrice()
        if (price != null) {
            view.text =
                "$selectedAmount BTC = ${(price * selectedAmount).roundTo(2)}$"
        }
    }

    private fun updateArrow(isPositiveTrend: Boolean) {
        context?.let {
            binding.arrow.setImageDrawable(
                AppCompatResources.getDrawable(
                    it,
                    if (isPositiveTrend) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
                )
            )
        }
    }

    private fun setListeners() {
        with(binding) {
            buttonBuyBtc.setOnClickListener {
                showDialogAmount {
                    try {
                        stockViewModel.buyBtc(
                            getAmount(it),
                            (activity as MainActivity).userLiveData.value,
                            getActualPrice()
                        )
                    } catch (e: BuyingException) {
                        showError(e)
                    }
                }
            }
            buttonSellBtc.setOnClickListener {
                showDialogAmount {
                    try {
                        stockViewModel.sellBtc(
                            getAmount(it),
                            (activity as MainActivity).userLiveData.value,
                            getActualPrice()
                        )
                    } catch (e: BuyingException) {
                        showError(e)
                    }
                }
            }
        }
    }

    private fun getAmount(text: Editable): Float? {
        try {
            return parseAmount(text)
        } catch (e: Exception) {
            showEmptyAmountError()
        }
        return null
    }

    private fun parseAmount(text: Editable?): Float {
        if (text.toString().isNotEmpty()) {
            return text.toString().replace(",", ".").toFloat()
        } else {
            throw Exception()
        }
    }

    private fun showEmptyAmountError() {
        showToast(getString(R.string.amount_error))
    }

    private fun showDialogAmount(onOkClick: (Editable) -> Unit) {
        context?.let { context ->
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.edit_text_dialog, null)
            val alertDialogBuilder = AlertDialog.Builder(context)

            alertDialogBuilder.setView(view)

            val amountTextView = view.findViewById<TextView>(R.id.infoAmountInDialog)
            val userInput = view.findViewById<EditText>(R.id.editText).apply {
                addTextChangedListener {
                    try {
                        showInfoAmount(amountTextView, parseAmount(it))
                    } catch (e: Exception) {
                        showInfoAmount(amountTextView)
                    }
                }
            }

            alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(
                    getString(R.string.ok)
                ) { _, _ ->
                    onOkClick.invoke(userInput.text)
                }
                .setNegativeButton(
                    getString(R.string.cancel)
                ) { dialog, _ ->
                    dialog.cancel()
                }?.apply {
                    create()
                    show()
                    showInfoAmount(amountTextView)
                }
        }
    }
}