package com.singlelab.bitcoinbrawler.ui.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.singlelab.bitcoinbrawler.databinding.FragmentStoreBinding
import com.singlelab.bitcoinbrawler.ui.store.adapter.ProductsAdapter

class StoreFragment : Fragment() {

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

        observeViewModel()

        return binding.root
    }

    private fun observeViewModel() {
        storeViewModel.products.observe(viewLifecycleOwner, {
            with(binding.recyclerView) {
                layoutManager = LinearLayoutManager(context)
                adapter = ProductsAdapter(it)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}