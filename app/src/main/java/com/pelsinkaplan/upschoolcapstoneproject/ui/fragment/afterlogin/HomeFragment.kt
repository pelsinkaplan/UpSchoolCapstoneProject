package com.pelsinkaplan.upschoolcapstoneproject.ui.fragment.afterlogin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.pelsinkaplan.upschoolcapstoneproject.databinding.FragmentHomeBinding
import com.pelsinkaplan.upschoolcapstoneproject.data.model.Product
import com.pelsinkaplan.upschoolcapstoneproject.ui.adapter.HomeProductAdapter
import com.pelsinkaplan.upschoolcapstoneproject.ui.adapter.listener.OnProductNavigateClickListener
import com.pelsinkaplan.upschoolcapstoneproject.ui.viewmodel.afterlogin.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: HomeProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: HomeViewModel by activityViewModels()
        this.viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var list = arrayListOf<Product>()
        Timber.tag("Current User").e(FirebaseAuth.getInstance().currentUser!!.uid)
        binding.apply {
            CoroutineScope(Dispatchers.Main).launch {
                val response = viewModel.service()
                if (response != null)
                    list = viewModel.service()!!
                adapter = HomeProductAdapter(list, object : OnProductNavigateClickListener {
                    override fun navigate(product: Product) {
                        val action =
                            HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(product)
                        Navigation.findNavController(view).navigate(action)
                    }

                })
                val layoutManager = LinearLayoutManager(context)
                productRecyclerView.layoutManager = layoutManager
                layoutManager.orientation = LinearLayoutManager.HORIZONTAL
                progressBar.visibility = View.GONE
                productRecyclerView.adapter = adapter
            }
            saleProductsButton.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToProductsFragment("sale")
                Navigation.findNavController(it).navigate(action)
            }
        }
    }
}