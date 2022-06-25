package com.pelsinkaplan.upschoolcapstoneproject.ui.fragment.afterlogin

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.pelsinkaplan.upschoolcapstoneproject.R
import com.pelsinkaplan.upschoolcapstoneproject.databinding.FragmentProductDetailBinding
import com.pelsinkaplan.upschoolcapstoneproject.ui.viewmodel.afterlogin.ProductDetailViewModel
import com.pelsinkaplan.upschoolcapstoneproject.data.model.Product
import com.pelsinkaplan.upschoolcapstoneproject.service.room.FavoritesDatabase
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
    private lateinit var favoritesDatabase: FavoritesDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: ProductDetailViewModel by activityViewModels()
        this.viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        favoritesDatabase = FavoritesDatabase.getFavoritesDatabase(requireContext())!!
        val favoritesList = favoritesDatabase.getFavoritesDao().getAll()
        val args: ProductDetailFragmentArgs by navArgs()
        val product = args.product
        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        binding.apply {
            productTitleCustomTextView.apply {
                setTextViewVisible()
                setTextViewText(product.title)
                setTextViewBold()
                setTextViewSize(20F)
            }
            productPriceCustomTextView.apply {
                setTextViewVisible()
                setTextViewText("$" + product.price)
                setTextViewBold()
                setTextViewSize(20F)
            }
            productDescriptionCustomTextView.apply {
                setTextViewVisible()
                setTextViewText(product.description)
                setTextViewSize(16F)
            }
            bottomProductImageCustomImageView.apply {
                Glide.with(this).load(product.image)
                    .into(imageView)
                setImageViewVisible()
            }
            topProductImageCustomImageView.apply {
                Glide.with(this).load(product.image)
                    .into(imageView)
                setImageViewVisible()
            }

            if (favoritesList.contains(product)) {
                addToFavoritesButton.apply {
                    setBackgroundResource(R.drawable.ic_favorite)
                    setOnClickListener {
                        favoritesDatabase.getFavoritesDao().delete(product)
                        setBackgroundResource(R.drawable.ic_not_favorite)
                    }
                }
            } else {
                addToFavoritesButton.apply {
                    setBackgroundResource(R.drawable.ic_not_favorite)
                    setOnClickListener {
                        favoritesDatabase.getFavoritesDao().insert(product)
                        setBackgroundResource(R.drawable.ic_favorite)
                    }
                }
            }

            addToChartButton.isEnabled = true
            addToChartButton.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    val bag = viewModel.getUserBag(userId)!!
                    var state = true
                    for (item in bag) {
                        if (item.title == product.title) {
                            viewModel.deleteFromBag(item.id)
                            item.count += 1
                            product.count = item.count
                            state = false
                        }
                    }
                    if (state) {
                        product.count = 1
                    }
                    viewModel.addToBag(userId, product)
                }

                binding.topProductImageCustomImageView.startAnimation(
                    AnimationUtils.loadAnimation(
                        requireContext(),
                        R.anim.add_to_chart
                    )
                )
                binding.bottomProductImageCustomImageView.startAnimation(
                    AnimationUtils.loadAnimation(
                        requireContext(),
                        R.anim.product_detail_image_anim
                    )
                )
            }
        }
    }
}