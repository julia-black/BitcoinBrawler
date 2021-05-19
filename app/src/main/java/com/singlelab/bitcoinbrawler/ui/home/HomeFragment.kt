package com.singlelab.bitcoinbrawler.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.singlelab.bitcoinbrawler.databinding.FragmentHomeBinding
import java.util.*

class HomeFragment : Fragment() {

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

        runTimer()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun runTimer() {
        val task: TimerTask = object : TimerTask() {
            override fun run() {
                activity?.runOnUiThread {
                    homeViewModel.updateUser()
                }
            }
        }
        val timer = Timer()
        timer.schedule(task, 0, 1000)
    }

    private fun observeViewModel() {
        homeViewModel.user.observe(viewLifecycleOwner, {
            with(binding) {
                balanceBtc.text = "${it.amountBtc} BTC"
                balanceDollars.text = "${it.amountDollar} $"
                velocity.text = "${it.velocity} BTC/sec"
            }
        })
    }
}