package com.pelsinkaplan.upschoolcapstoneproject.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pelsinkaplan.upschoolcapstoneproject.data.model.Product
import com.pelsinkaplan.upschoolcapstoneproject.databinding.ItemHomeProductBinding
import com.pelsinkaplan.upschoolcapstoneproject.databinding.ItemProductBinding
import com.pelsinkaplan.upschoolcapstoneproject.ui.adapter.listener.OnProductNavigateClickListener
import com.pelsinkaplan.upschoolcapstoneproject.ui.fragment.afterlogin.HomeFragmentDirections

/**
 * Created by Pelşin KAPLAN on 16.06.2022.
 */
class HomeProductAdapter(
    var products: ArrayList<Product>,
    private val onProductNavigateClickListener: OnProductNavigateClickListener
) :
    RecyclerView.Adapter<HomeProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHomeProductBinding.inflate(inflater, parent, false)
        return HomeProductViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HomeProductViewHolder, position: Int) {
        val product = products[position]
        holder.binding.apply {
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


class HomeProductViewHolder(val binding: ItemHomeProductBinding) :
    RecyclerView.ViewHolder(binding.root)
