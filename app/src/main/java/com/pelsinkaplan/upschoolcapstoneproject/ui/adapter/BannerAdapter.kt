package com.pelsinkaplan.upschoolcapstoneproject.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pelsinkaplan.upschoolcapstoneproject.R
import com.pelsinkaplan.upschoolcapstoneproject.data.model.BannerModel
import com.pelsinkaplan.upschoolcapstoneproject.databinding.ItemBannerBinding
import com.pelsinkaplan.upschoolcapstoneproject.databinding.ItemCategoryBinding
import com.pelsinkaplan.upschoolcapstoneproject.ui.adapter.listener.OnCategoryNavigateListener
import com.pelsinkaplan.upschoolcapstoneproject.ui.adapter.listener.OnNavigateListener

/**
 * Created by Pelşin KAPLAN on 28.06.2022.
 */
class BannerAdapter(
    var bannerList: List<BannerModel>, private val onNavigateListener: OnNavigateListener
) :
    RecyclerView.Adapter<BannerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBannerBinding.inflate(inflater, parent, false)
        return BannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val banner = bannerList[position]

        holder.binding.apply {
            bannerImageView.setBackgroundResource(banner.id)
            bannerDescTextView.text = banner.text
            if (position == 2)
                letsShopButton.visibility = View.VISIBLE
            letsShopButton.setOnClickListener {
                onNavigateListener.navigate()
            }
        }
    }

    override fun getItemCount(): Int {
        return bannerList.size
    }

}

class BannerViewHolder(val binding: ItemBannerBinding) :
    RecyclerView.ViewHolder(binding.root)