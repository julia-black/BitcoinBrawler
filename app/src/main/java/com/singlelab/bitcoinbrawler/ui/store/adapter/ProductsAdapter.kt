package com.singlelab.bitcoinbrawler.ui.store.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.singlelab.bitcoinbrawler.R
import com.singlelab.bitcoinbrawler.model.Product

class ProductsAdapter(private val list: List<Product>) :
    RecyclerView.Adapter<ProductViewHolder>() {

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.view_item_product, viewGroup, false)

        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ProductViewHolder, position: Int) {
        val product = list[position]
        viewHolder.nameTextView.text = product.name
        viewHolder.priceTextView.text = product.price.toString()
    }
}