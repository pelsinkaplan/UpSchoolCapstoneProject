package com.pelsinkaplan.upschoolcapstoneproject.ui.viewmodel.afterlogin

import androidx.lifecycle.ViewModel
import com.pelsinkaplan.upschoolcapstoneproject.data.model.Product
import com.pelsinkaplan.upschoolcapstoneproject.service.network.RetrofitAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Pelşin KAPLAN on 14.06.2022.
 */
@HiltViewModel
class ProductsViewModel @Inject constructor(private val retrofitAPI: RetrofitAPI) : ViewModel() {
    suspend fun service(category: String): List<Product>? {
        val dataResponse = retrofitAPI.getProductsByUserAndCategory("bazaarapp", category)
        if (dataResponse.isSuccessful)
            return dataResponse.body()
        return null
    }

    suspend fun getAllProducts(): List<Product>? {
        val dataResponse = retrofitAPI.getProductsByUser("bazaarapp")
        if (dataResponse.isSuccessful)
            return dataResponse.body()
        return null
    }

    suspend fun getSaleProducts(): List<Product>? {
        val dataResponse = retrofitAPI.getSaleProductsByUser("bazaarapp")
        if (dataResponse.isSuccessful)
            return dataResponse.body()
        return null
    }
}