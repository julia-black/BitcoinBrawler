package com.singlelab.bitcoinbrawler.ui.store.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.singlelab.bitcoinbrawler.R
import com.singlelab.bitcoinbrawler.model.Product
import com.singlelab.bitcoinbrawler.util.getDrawableByProduct

class ProductsAdapter(
    private val products: List<Product>,
    private val userProducts: List<Product>?,
    private val userDollars: Float,
    private val onBuyClick: (Product) -> Unit
) :
    RecyclerView.Adapter<ProductViewHolder>() {

    override fun getItemCount() = products.size

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.view_item_product, viewGroup, false)

        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ProductViewHolder, position: Int) {
        val product = products[position]
        with(viewHolder) {
            nameTextView.text = product.title
            priceTextView.text = "${product.price} $"
            descriptionTextView.text = product.description
            image.setImageDrawable(image.context.getDrawableByProduct(product))
            buyButton.setOnClickListener {
                onBuyClick.invoke(product)
            }
            buyButton.isEnabled =
                !(!userProducts.isNullOrEmpty() && userProducts.contains(product)) && product.price <= userDollars
        }
    }
}