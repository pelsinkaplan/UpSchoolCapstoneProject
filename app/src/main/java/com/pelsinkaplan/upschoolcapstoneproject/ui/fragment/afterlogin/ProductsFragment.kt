package com.pelsinkaplan.upschoolcapstoneproject.ui.fragment.afterlogin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.pelsinkaplan.upschoolcapstoneproject.R
import com.pelsinkaplan.upschoolcapstoneproject.databinding.FragmentChartBinding
import com.pelsinkaplan.upschoolcapstoneproject.databinding.FragmentProductDetailBinding
import com.pelsinkaplan.upschoolcapstoneproject.databinding.FragmentProductsBinding
import com.pelsinkaplan.upschoolcapstoneproject.ui.adapter.CategoriesAdapter
import com.pelsinkaplan.upschoolcapstoneproject.ui.adapter.ChartAdapter
import com.pelsinkaplan.upschoolcapstoneproject.ui.adapter.ProductAdapter
import com.pelsinkaplan.upschoolcapstoneproject.ui.viewmodel.afterlogin.ChartViewModel
import com.pelsinkaplan.upschoolcapstoneproject.ui.viewmodel.afterlogin.ProductDetailViewModel
import com.pelsinkaplan.upschoolcapstoneproject.ui.viewmodel.afterlogin.ProductsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ProductsFragment : Fragment() {
    private lateinit var binding: FragmentProductsBinding
    private lateinit var viewModel: ProductsViewModel
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: ProductsViewModel by activityViewModels()
        this.viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: ProductsFragmentArgs by navArgs()
        val category = args.category
        CoroutineScope(Dispatchers.Main).launch {
            val products = viewModel.service(category)!!
            Timber.tag("PRODUCTS").e(products.toString())
            adapter = ProductAdapter(products)
            val layoutManager = GridLayoutManager(requireContext(), 2)
            binding.chartRecyclerView.layoutManager = layoutManager
            layoutManager.orientation = GridLayoutManager.VERTICAL
            binding.chartRecyclerView.adapter = adapter
        }
    }
}