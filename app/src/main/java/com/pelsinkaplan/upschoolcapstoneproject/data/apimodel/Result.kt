package com.pelsinkaplan.upschoolcapstoneproject.data.apimodel

import com.pelsinkaplan.upschoolcapstoneproject.data.model.Product

/**
 * Created by Pelşin KAPLAN on 19.06.2022.
 */
data class Result(
    var productList: List<Product>,
    var status: Int,
    var message: String
)
