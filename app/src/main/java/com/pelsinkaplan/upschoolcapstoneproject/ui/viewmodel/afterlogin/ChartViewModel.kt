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
class ChartViewModel @Inject constructor(private val retrofitAPI: RetrofitAPI) : ViewModel() {
    suspend fun service(user: String): List<Product>? {
        val dataResponse = retrofitAPI.getBagProductsByUser(user)
        Timber.tag("BAG CONTENT").e(dataResponse.body().toString())
        if (dataResponse.isSuccessful)
            return dataResponse.body()
        return null
    }

    suspend fun deleteProductFromBag(id: Int) {
        Timber.tag("DELETED ITEM").e(id.toString())
        val dataResponse = retrofitAPI.deleteFromBag(id)
        Timber.tag("BAG CONTENT").e(dataResponse.message)
    }

    suspend fun addProductBag(product: Product, userId: String) {
        val dataResponse = retrofitAPI.addToBag(
            userId,
            product.title,
            product.price,
            product.description,
            product.category,
            product.image,
            product.rate,
            product.count,
            product.sale_state
        )
        Timber.tag("BAG CONTENT").e(dataResponse.message)
    }
}