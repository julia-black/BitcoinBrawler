package com.singlelab.bitcoinbrawler.ui.home

import android.content.res.AssetManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.singlelab.bitcoinbrawler.MainActivity
import com.singlelab.bitcoinbrawler.databinding.FragmentHomeBinding
import com.singlelab.bitcoinbrawler.model.Product
import com.singlelab.bitcoinbrawler.ui.base.BaseFragment
import pl.droidsonroids.gif.GifDrawable


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
                it.getPepe()?.let { product ->
                    val pepeDrawable = getPepeDrawable(this.assets, product)
                    binding.pepe.setImageDrawable(pepeDrawable)
                }
                //binding.drill.text = it.getDrill()
            }
        })
    }

    private fun getPepeDrawable(assets: AssetManager, product: Product): GifDrawable {
        return GifDrawable(
            assets, when (product.id) {
                0 -> "pepe_default.gif"
                else -> "pepe_default.gif"
            }
        )
    }
}