package com.pelsinkaplan.upschoolcapstoneproject.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pelsinkaplan.upschoolcapstoneproject.databinding.ItemCategoryBinding
import com.pelsinkaplan.upschoolcapstoneproject.ui.adapter.listener.OnCategoryNavigateListener

/**
 * Created by Pelşin KAPLAN on 13.06.2022.
 */
class CategoriesAdapter(
    var categories: List<String>,
    private val onCategoryNavigateListener: OnCategoryNavigateListener
) :
    RecyclerView.Adapter<CategoriesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding.inflate(inflater, parent, false)
        return CategoriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val category = categories[position]


        holder.binding.apply {
            categoryTitleTextView.text = category
            categoryCardView.setOnClickListener {
                onCategoryNavigateListener.navigate(category)
            }
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: MutableList<String>) {
        categories = list
        notifyDataSetChanged()
    }

}

class CategoriesViewHolder(val binding: ItemCategoryBinding) :
    RecyclerView.ViewHolder(binding.root)

