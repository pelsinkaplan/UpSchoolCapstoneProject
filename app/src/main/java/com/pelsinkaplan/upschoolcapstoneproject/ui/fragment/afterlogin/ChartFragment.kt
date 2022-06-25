package com.pelsinkaplan.upschoolcapstoneproject.ui.fragment.afterlogin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.work.*
import com.google.firebase.auth.FirebaseAuth
import com.pelsinkaplan.upschoolcapstoneproject.data.model.Product
import com.pelsinkaplan.upschoolcapstoneproject.databinding.FragmentChartBinding
import com.pelsinkaplan.upschoolcapstoneproject.service.workmanager.BagWorker
import com.pelsinkaplan.upschoolcapstoneproject.ui.adapter.ChartAdapter
import com.pelsinkaplan.upschoolcapstoneproject.ui.adapter.listener.OnChartItemListener
import com.pelsinkaplan.upschoolcapstoneproject.ui.adapter.listener.OnProductNavigateClickListener
import com.pelsinkaplan.upschoolcapstoneproject.ui.viewmodel.afterlogin.ChartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class ChartFragment : Fragment() {
    private lateinit var binding: FragmentChartBinding
    private lateinit var viewModel: ChartViewModel
    private lateinit var adapter: ChartAdapter
    private var totalAmount: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: ChartViewModel by activityViewModels()
        this.viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        CoroutineScope(Dispatchers.Main).launch {
            val chartList: List<Product> = viewModel.service(userId)!!
            binding.summaryCustomProfileDetailLine.setTitleTextView("Total Amount")
            binding.checkOutButton.setOnClickListener {
                val action =
                    ChartFragmentDirections.actionChartFragmentToPaymentFragment(
                        totalAmount.toFloat()
                    )
                Navigation.findNavController(view).navigate(action)
            }
            adapter = ChartAdapter(chartList, object : OnChartItemListener {
                override fun plusProduct(product: Product) {
                    CoroutineScope(Dispatchers.Main).launch {
                        product.count += 1
                        viewModel.deleteProductFromBag(product.id)
                        viewModel.addProductBag(product, userId)
                        updateTotalAmount(userId)
                    }
                }

                override fun minusProduct(product: Product) {
                    CoroutineScope(Dispatchers.Main).launch {
                        if (product.count > 1) {
                            product.count -= 1
                            viewModel.deleteProductFromBag(product.id)
                            viewModel.addProductBag(product, userId)
                            updateTotalAmount(userId)
                        }
                    }
                }

                override fun deleteProduct(product: Product) {
                    CoroutineScope(Dispatchers.Main).launch {
                        viewModel.deleteProductFromBag(product.id)
                        updateTotalAmount(userId)
                    }
                }
            }, object : OnProductNavigateClickListener {
                override fun navigate(product: Product) {
                    val action =
                        ChartFragmentDirections.actionChartFragmentToProductDetailFragment(product)
                    Navigation.findNavController(view).navigate(action)
                }
            })
            updateTotalAmount(userId)
            binding.chartRecyclerView.adapter = adapter
        }
    }

    private suspend fun updateTotalAmount(userId: String) {
        totalAmount = 0.0
        val chartList = viewModel.service(userId)!!
        adapter.updateList(chartList)
        for (product in chartList) {
            totalAmount += (product.price * product.count)
        }
        if (chartList.isEmpty()) {
            binding.checkOutButton.isEnabled = false
            binding.emptyTextView.visibility = View.VISIBLE
        } else {
            binding.checkOutButton.isEnabled = true
            binding.emptyTextView.visibility = View.GONE
        }
        binding.summaryCustomProfileDetailLine.setDetailTextView("$$totalAmount")
    }
}