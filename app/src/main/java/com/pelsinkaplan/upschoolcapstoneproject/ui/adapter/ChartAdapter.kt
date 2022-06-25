package com.pelsinkaplan.upschoolcapstoneproject.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pelsinkaplan.upschoolcapstoneproject.data.model.Product
import com.pelsinkaplan.upschoolcapstoneproject.databinding.ItemChartBinding
import com.pelsinkaplan.upschoolcapstoneproject.ui.adapter.listener.OnChartItemListener
import com.pelsinkaplan.upschoolcapstoneproject.ui.adapter.listener.OnProductNavigateClickListener

/**
 * Created by Pelşin KAPLAN on 14.06.2022.
 */
class ChartAdapter(
    var products: List<Product>,
    private val onChartItemListener: OnChartItemListener,
    private val onNavigateClickListener: OnProductNavigateClickListener
) :
    RecyclerView.Adapter<ChartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemChartBinding.inflate(inflater, parent, false)
        return ChartViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ChartViewHolder, position: Int) {
        val product = products[position]

        holder.binding.apply {
            productTitleTextView.text = product.title
            val totalPrice = product.price * product.count
            productPriceTextView.text = "$$totalPrice"
            productAmountTextview.text = product.count.toString()
            Glide.with(this.root).load(product.image).into(productImgImageView)
            val newAmount = product.count
            minusButton.setOnClickListener {
                productAmountTextview.text = newAmount.toString()
                product.count = newAmount
                onChartItemListener.minusProduct(product)
            }
            plusButton.setOnClickListener {
                productAmountTextview.text = newAmount.toString()
                product.count = newAmount
                onChartItemListener.plusProduct(product)

            }
            deleteButton.setOnClickListener {
                onChartItemListener.deleteProduct(product)
            }
            chartCardView.setOnClickListener {
                onNavigateClickListener.navigate(product)
            }
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<Product>) {
        products = list
        notifyDataSetChanged()
    }

}

class ChartViewHolder(val binding: ItemChartBinding) :
    RecyclerView.ViewHolder(binding.root)

