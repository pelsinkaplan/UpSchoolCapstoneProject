package com.pelsinkaplan.upschoolcapstoneproject.ui.fragment.afterlogin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.pelsinkaplan.upschoolcapstoneproject.R
import com.pelsinkaplan.upschoolcapstoneproject.databinding.FragmentChartBinding
import com.pelsinkaplan.upschoolcapstoneproject.databinding.FragmentProductDetailBinding
import com.pelsinkaplan.upschoolcapstoneproject.ui.adapter.CategoriesAdapter
import com.pelsinkaplan.upschoolcapstoneproject.ui.adapter.ChartAdapter
import com.pelsinkaplan.upschoolcapstoneproject.ui.adapter.ProductAdapter
import com.pelsinkaplan.upschoolcapstoneproject.ui.viewmodel.afterlogin.ChartViewModel
import com.pelsinkaplan.upschoolcapstoneproject.ui.viewmodel.afterlogin.ProductDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ChartFragment : Fragment() {
    private lateinit var binding: FragmentChartBinding
    private lateinit var viewModel: ChartViewModel
    private lateinit var adapter: ChartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: ChartViewModel by activityViewModels()
        this.viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            val chart = viewModel.service(requireContext())
            Timber.tag("CHART").e(chart.toString())
            adapter = ChartAdapter(chart)
            binding.chartRecyclerView.adapter = adapter
        }
    }
}