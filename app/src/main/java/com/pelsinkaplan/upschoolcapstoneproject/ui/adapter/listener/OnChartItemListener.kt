package com.pelsinkaplan.upschoolcapstoneproject.ui.adapter.listener

import com.pelsinkaplan.upschoolcapstoneproject.data.model.Product

/**
 * Created by Pelşin KAPLAN on 22.06.2022.
 */
interface OnChartItemListener {
    fun plusProduct(product: Product)

    fun minusProduct(product: Product)

    fun deleteProduct(product: Product)
}