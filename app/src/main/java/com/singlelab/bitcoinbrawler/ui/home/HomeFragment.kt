package com.singlelab.bitcoinbrawler.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.singlelab.bitcoinbrawler.MainActivity
import com.singlelab.bitcoinbrawler.databinding.FragmentHomeBinding
import com.singlelab.bitcoinbrawler.model.Product
import com.singlelab.bitcoinbrawler.ui.base.BaseFragment
import com.singlelab.bitcoinbrawler.util.getDrawableRes


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

        observeUser()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeUser() {
        (activity as MainActivity).userLiveData.observe(viewLifecycleOwner, {
            context?.apply {
                it.getPepe()?.let {
                    Glide
                        .with(this)
                        .asGif()
                        .load(it.getDrawableRes())
                        .into(binding.pepe)
                }

                it.getDrill()?.let {
                    Glide
                        .with(this)
                        .asGif()
                        .load(it.getDrawableRes())
                        .into(binding.drill)
                }

                with(binding) {
                    it.getOtherProducts().forEach {
                        when (it) {
                            Product.MONSTER -> monster.isVisible = true
                            Product.SHLEPPA -> shleppa.isVisible = true
                            Product.GUCCI -> gucci.isVisible = true
                            Product.SPIDY -> spidy.isVisible = true
                            Product.SHREK -> shrek.isVisible = true
                            Product.DOGE -> doge.isVisible = true
                        }
                    }
                }
            }
        })
    }
}