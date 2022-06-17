package com.pelsinkaplan.upschoolcapstoneproject.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pelsinkaplan.upschoolcapstoneproject.data.model.Product
import com.pelsinkaplan.upschoolcapstoneproject.databinding.ItemProductBinding
import com.pelsinkaplan.upschoolcapstoneproject.ui.fragment.afterlogin.HomeFragmentDirections

/**
 * Created by Pelşin KAPLAN on 16.06.2022.
 */
class NewProductAdapter(var products: ArrayList<Product>) :
    RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.binding.apply {
            productTitleTextView.text = product.title
            productPriceTextView.text = "$" + product.price
            Glide.with(this.root).load(product.image).into(productImageView)
            productCardView.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(product.id.toString())
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }
}