package com.pelsinkaplan.upschoolcapstoneproject.ui.fragment.afterlogin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pelsinkaplan.upschoolcapstoneproject.ui.adapter.ProductAdapter
import com.pelsinkaplan.upschoolcapstoneproject.databinding.FragmentHomeBinding
import com.pelsinkaplan.upschoolcapstoneproject.data.model.Product
import com.pelsinkaplan.upschoolcapstoneproject.ui.adapter.NewProductAdapter
import com.pelsinkaplan.upschoolcapstoneproject.ui.viewmodel.afterlogin.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: NewProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: HomeViewModel by activityViewModels()
        this.viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var list = arrayListOf<Product>()

        CoroutineScope(Dispatchers.Main).launch {
            val response = viewModel.service()
            if (response != null)
                list = viewModel.service()!!
            adapter = NewProductAdapter(list)
            val layoutManager = LinearLayoutManager(context)
            binding.productRecyclerView.layoutManager = layoutManager
            layoutManager.orientation = LinearLayoutManager.HORIZONTAL
            binding.progressBar.visibility = View.GONE
            binding.productRecyclerView.adapter = adapter
        }
    }
}