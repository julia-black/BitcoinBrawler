package com.singlelab.bitcoinbrawler.ui.store.adapter

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.singlelab.bitcoinbrawler.R

class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val priceTextView: TextView = view.findViewById(R.id.productPrice)
    val nameTextView: TextView = view.findViewById(R.id.productName)
    val buyButton: Button = view.findViewById(R.id.buttonBuy)
}