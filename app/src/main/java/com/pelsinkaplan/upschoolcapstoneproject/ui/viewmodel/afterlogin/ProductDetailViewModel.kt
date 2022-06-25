package com.pelsinkaplan.upschoolcapstoneproject.ui.viewmodel.afterlogin

import androidx.lifecycle.ViewModel
import com.pelsinkaplan.upschoolcapstoneproject.data.model.Product
import com.pelsinkaplan.upschoolcapstoneproject.service.network.RetrofitAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Pelşin KAPLAN on 13.06.2022.
 */
@HiltViewModel
class ProductDetailViewModel @Inject constructor(private val retrofitAPI: RetrofitAPI) :
    ViewModel() {

    suspend fun addToBag(user: String, product: Product) {
        Timber.tag("Add To Bag").e(
            retrofitAPI.addToBag(
                user,
                product.title,
                product.price,
                product.description,
                product.category,
                product.image,
                product.price,
                product.count,
                product.sale_state
            ).message
        )
    }

    suspend fun deleteFromBag(id: Int) {
        Timber.tag("Delete From Bag").e(
            retrofitAPI.deleteFromBag(
                id
            ).message
        )
    }

    suspend fun getUserBag(user: String): List<Product>? {
        val dataResponse = retrofitAPI.getBagProductsByUser(user)
        if (dataResponse.isSuccessful)
            return dataResponse.body()
        return null
    }
}