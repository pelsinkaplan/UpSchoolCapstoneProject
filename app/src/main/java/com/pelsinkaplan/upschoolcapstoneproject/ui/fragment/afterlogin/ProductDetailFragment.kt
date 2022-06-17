package com.pelsinkaplan.upschoolcapstoneproject.ui.fragment.afterlogin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.pelsinkaplan.upschoolcapstoneproject.R
import com.pelsinkaplan.upschoolcapstoneproject.data.model.Product
import com.pelsinkaplan.upschoolcapstoneproject.data.model.ProductChart
import com.pelsinkaplan.upschoolcapstoneproject.databinding.FragmentHomeBinding
import com.pelsinkaplan.upschoolcapstoneproject.databinding.FragmentProductDetailBinding
import com.pelsinkaplan.upschoolcapstoneproject.service.room.ChartDatabase
import com.pelsinkaplan.upschoolcapstoneproject.ui.adapter.ProductAdapter
import com.pelsinkaplan.upschoolcapstoneproject.ui.viewmodel.afterlogin.HomeViewModel
import com.pelsinkaplan.upschoolcapstoneproject.ui.viewmodel.afterlogin.ProductDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {
    private lateinit var binding: FragmentProductDetailBinding
    private lateinit var viewModel: ProductDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: ProductDetailViewModel by activityViewModels()
        this.viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: ProductDetailFragmentArgs by navArgs()
        val productId = args.productId

        CoroutineScope(Dispatchers.Main).launch {
            val response = viewModel.service(productId)!!
            binding.apply {
                productTitleCustomTextView.apply {
                    setTextViewVisible()
                    setTextViewText(response.title)
                    setTextViewBold()
                    setTextViewSize(20F)
                }
                productPriceCustomTextView.apply {
                    setTextViewVisible()
                    setTextViewText("$" + response.price)
                    setTextViewBold()
                    setTextViewSize(20F)
                }
                productDescriptionCustomTextView.apply {
                    setTextViewVisible()
                    setTextViewText(response.description)
                    setTextViewSize(16F)
                }
                productImageCustomImageView.apply {
                    Glide.with(this).load(response.image)
                        .into(imageView)
                    setImageViewVisible()
                }
                var state = false // This part will be edit
                if (state) {
                    addToFavoritesButton.setBackgroundResource(R.drawable.ic_favorite)
                } else {
                    addToFavoritesButton.setBackgroundResource(R.drawable.ic_not_favorite)
                }


                addToChartButton.isEnabled = true
                addToChartButton.setOnClickListener {
                    val chartDatabase = ChartDatabase.getChartDatabase(requireContext())!!
                    var amount = 1
                    try {
                        val chart = chartDatabase.getChartDao().getAll()

                        for (productChart in chart) {
                            if (productChart.id == response.id) {
                                amount += productChart.amount
                                chartDatabase.getChartDao().update(
                                    ProductChart(
                                        response.id,
                                        response.title,
                                        response.price,
                                        response.image,
                                        amount
                                    )
                                )
                            } else {
                                chartDatabase.getChartDao().insert(
                                    ProductChart(
                                        response.id,
                                        response.title,
                                        response.price,
                                        response.image,
                                        amount
                                    )
                                )
                            }
                        }
                        if (chart.isEmpty())
                            chartDatabase.getChartDao().insert(
                                ProductChart(
                                    response.id,
                                    response.title,
                                    response.price,
                                    response.image,
                                    amount
                                )
                            )
                    } catch (e: Exception) {
                        Timber.tag("Chart").e(e.message)
                    }
                }
            }
        }
    }
}