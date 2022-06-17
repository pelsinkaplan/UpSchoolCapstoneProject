package com.pelsinkaplan.upschoolcapstoneproject.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Pelşin KAPLAN on 13.06.2022.
 */
@Entity(tableName = "chart_table")
data class ProductChart(
    @ColumnInfo(name = "product_id")
    @PrimaryKey
    var id: Int,
    @ColumnInfo(name = "product_title")
    var title: String,
    @ColumnInfo(name = "product_price")
    var price: String,
    @ColumnInfo(name = "product_image")
    var image: String,
    @ColumnInfo(name = "product_amount")
    var amount: Int
)
