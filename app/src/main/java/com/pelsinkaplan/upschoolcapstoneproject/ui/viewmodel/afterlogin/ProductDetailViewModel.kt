package com.pelsinkaplan.upschoolcapstoneproject.ui.viewmodel.afterlogin

import androidx.lifecycle.ViewModel
import com.pelsinkaplan.upschoolcapstoneproject.data.model.Product
import com.pelsinkaplan.upschoolcapstoneproject.service.network.RetrofitAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Pelşin KAPLAN on 13.06.2022.
 */
@HiltViewModel
class ProductDetailViewModel @Inject constructor(private val retrofitAPI: RetrofitAPI) :
    ViewModel() {
    suspend fun service(id: String): Product? {
        val dataResponse = retrofitAPI.getProductById(id)
        if (dataResponse.isSuccessful)
            return dataResponse.body()
        return null
    }
}