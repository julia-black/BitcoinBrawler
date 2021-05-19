package com.singlelab.bitcoinbrawler.ui.stock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.singlelab.bitcoinbrawler.databinding.FragmentStockBinding

class StockFragment : Fragment() {

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

        return root
    }

    private fun observeViewModel() {
        stockViewModel.prices.observe(viewLifecycleOwner, {
            var prices = ""
            it.forEach { price ->
                prices = "$prices $price"
            }
            binding.textDashboard.text = prices
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}