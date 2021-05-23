package com.singlelab.bitcoinbrawler.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.singlelab.bitcoinbrawler.MainActivity
import com.singlelab.bitcoinbrawler.databinding.FragmentHomeBinding
import com.singlelab.bitcoinbrawler.ui.base.BaseFragment
import com.singlelab.bitcoinbrawler.util.roundTo

class HomeFragment : BaseFragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        observeViewModel()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
        (activity as MainActivity).userLiveData.observe(viewLifecycleOwner, {
            with(binding) {
                balanceBtc.text = "${it.amountBtc} BTC"
                balanceDollars.text = "${(it.amountDollar).roundTo(2)} $"
                velocity.text = "${it.getAllVelocity()} BTC/sec"

                pepe.text = it.getPepe()
                drill.text = it.getDrill()
            }
        })
    }
}