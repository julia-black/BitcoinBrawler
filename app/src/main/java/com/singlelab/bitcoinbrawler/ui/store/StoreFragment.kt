package com.singlelab.bitcoinbrawler.ui.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.singlelab.bitcoinbrawler.MainActivity
import com.singlelab.bitcoinbrawler.databinding.FragmentStoreBinding
import com.singlelab.bitcoinbrawler.model.Product
import com.singlelab.bitcoinbrawler.model.exception.BuyingException
import com.singlelab.bitcoinbrawler.ui.base.BaseFragment
import com.singlelab.bitcoinbrawler.ui.store.adapter.ProductsAdapter

class StoreFragment : BaseFragment() {

    private lateinit var storeViewModel: StoreViewModel
    private var _binding: FragmentStoreBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        storeViewModel =
            ViewModelProvider(this).get(StoreViewModel::class.java)

        _binding = FragmentStoreBinding.inflate(inflater, container, false)

        observeUser()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeUser() {
        (activity as MainActivity).userLiveData.observe(viewLifecycleOwner, { user ->
            storeViewModel.setUserProducts(user.products)
            storeViewModel.productsLiveData.observe(viewLifecycleOwner, { products ->
                showLoading(false)
                with(binding.recyclerView) {
                    layoutManager = LinearLayoutManager(context)
                    adapter = ProductsAdapter(products, user.products, ::onItemClick)
                }
            })
        })
    }

    private fun onItemClick(product: Product) {
        showLoading()
        with(activity as MainActivity) {
            try {
                userLiveData.value?.buyProduct(product)
            } catch (e: BuyingException) {
                showLoading(false)
                showError(e)
            }
        }
    }

    private fun showLoading(isVisible: Boolean = true) {
        binding.loading.isVisible = isVisible
    }
}