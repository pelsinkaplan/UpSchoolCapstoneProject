package com.pelsinkaplan.upschoolcapstoneproject.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pelsinkaplan.upschoolcapstoneproject.data.model.ProductChart
import com.pelsinkaplan.upschoolcapstoneproject.databinding.ItemChartBinding
import com.pelsinkaplan.upschoolcapstoneproject.service.room.ChartDatabase
import com.pelsinkaplan.upschoolcapstoneproject.ui.fragment.afterlogin.ChartFragmentDirections

/**
 * Created by Pelşin KAPLAN on 14.06.2022.
 */
class ChartAdapter(var products: List<ProductChart>) : RecyclerView.Adapter<ChartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemChartBinding.inflate(inflater, parent, false)
        return ChartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChartViewHolder, position: Int) {
        val product = products[position]

        holder.binding.apply {
            productTitleTextView.text = product.title
            productPriceTextView.text = "$" + product.price
            productAmountTextview.text = product.amount.toString()
            Glide.with(this.root).load(product.image).into(productImgImageView)
            var newAmount = product.amount
            minusButton.setOnClickListener {
                if (product.amount > 1)
                    newAmount -= 1
                productAmountTextview.text = newAmount.toString()
                product.amount = newAmount
                val chartDatabase = ChartDatabase.getChartDatabase(it.context)!!
                chartDatabase.getChartDao().update(product)
            }
            plusButton.setOnClickListener {
                newAmount += 1
                productAmountTextview.text = newAmount.toString()
                product.amount = newAmount
                val chartDatabase = ChartDatabase.getChartDatabase(it.context)!!
                chartDatabase.getChartDao().update(product)

            }
            deleteButton.setOnClickListener {
                val chartDatabase = ChartDatabase.getChartDatabase(it.context)!!
                chartDatabase.getChartDao().delete(product)
                chartCardView.visibility = View.GONE
            }
            chartCardView.setOnClickListener {
                val action =
                    ChartFragmentDirections.actionChartFragmentToProductDetailFragment(product.id.toString())
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

}

class ChartViewHolder(val binding: ItemChartBinding) :
    RecyclerView.ViewHolder(binding.root)

