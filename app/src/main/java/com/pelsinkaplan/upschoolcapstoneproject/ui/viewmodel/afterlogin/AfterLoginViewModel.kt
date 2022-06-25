package com.pelsinkaplan.upschoolcapstoneproject.ui.viewmodel.afterlogin

import androidx.lifecycle.ViewModel
import com.pelsinkaplan.upschoolcapstoneproject.data.model.Product
import com.pelsinkaplan.upschoolcapstoneproject.service.network.RetrofitAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Pelşin KAPLAN on 23.06.2022.
 */
@HiltViewModel
class AfterLoginViewModel @Inject constructor(private val retrofitAPI: RetrofitAPI) : ViewModel() {
    suspend fun service(user: String): List<Product>? {
        val dataResponse = retrofitAPI.getBagProductsByUser(user)
        Timber.tag("BAG CONTENT").e(dataResponse.body().toString())
        if (dataResponse.isSuccessful)
            return dataResponse.body()
        return null
    }
}