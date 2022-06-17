package com.pelsinkaplan.upschoolcapstoneproject.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.pelsinkaplan.upschoolcapstoneproject.databinding.ItemCategoryBinding
import com.pelsinkaplan.upschoolcapstoneproject.ui.fragment.afterlogin.CategoriesFragmentDirections

/**
 * Created by Pelşin KAPLAN on 13.06.2022.
 */
class CategoriesAdapter(var categories: List<String>) :
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
                val action =
                    CategoriesFragmentDirections.actionCategoriesFragmentToProductsFragment(category)
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

}

class CategoriesViewHolder(val binding: ItemCategoryBinding) :
    RecyclerView.ViewHolder(binding.root)

