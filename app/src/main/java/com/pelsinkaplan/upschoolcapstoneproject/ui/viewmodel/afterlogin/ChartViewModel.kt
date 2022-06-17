package com.pelsinkaplan.upschoolcapstoneproject.ui.viewmodel.afterlogin

import android.content.Context
import androidx.lifecycle.ViewModel
import com.pelsinkaplan.upschoolcapstoneproject.data.model.ProductChart
import com.pelsinkaplan.upschoolcapstoneproject.service.network.RetrofitAPI
import com.pelsinkaplan.upschoolcapstoneproject.service.room.ChartDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Pelşin KAPLAN on 13.06.2022.
 */
@HiltViewModel
class ChartViewModel @Inject constructor(private val retrofitAPI: RetrofitAPI) : ViewModel() {
    fun service(context: Context): List<ProductChart> {
        val chartDatabase = ChartDatabase.getChartDatabase(context)!!
        return chartDatabase.getChartDao().getAll()
    }
}