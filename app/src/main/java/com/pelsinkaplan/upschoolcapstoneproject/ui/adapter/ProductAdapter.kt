package com.pelsinkaplan.upschoolcapstoneproject.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pelsinkaplan.upschoolcapstoneproject.databinding.ItemProductBinding
import com.pelsinkaplan.upschoolcapstoneproject.data.model.Product
import com.pelsinkaplan.upschoolcapstoneproject.ui.adapter.listener.OnProductNavigateClickListener
import com.pelsinkaplan.upschoolcapstoneproject.ui.fragment.afterlogin.ProductsFragmentDirections

/**
 * Created by Pelşin KAPLAN on 13.06.2022.
 */

class ProductAdapter(
    var products: List<Product>,
    private val onProductNavigateClickListener: OnProductNavigateClickListener
) : RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater, parent, false)
        return ProductViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.binding.apply {
            if (product.sale_state == 1)
                saleImageView.visibility = View.VISIBLE
            productTitleTextView.text = product.title
            productPriceTextView.text = "$" + product.price
            Glide.with(this.root).load(product.image).into(productImageView)
            productCardView.setOnClickListener {
                onProductNavigateClickListener.navigate(product)
            }
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }
}

class ProductViewHolder(val binding: ItemProductBinding) :
    RecyclerView.ViewHolder(binding.root)

