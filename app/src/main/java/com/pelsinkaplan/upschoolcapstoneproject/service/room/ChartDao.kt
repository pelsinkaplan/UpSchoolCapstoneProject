package com.pelsinkaplan.upschoolcapstoneproject.service.room

import androidx.room.*
import com.pelsinkaplan.upschoolcapstoneproject.data.model.ProductChart

/**
 * Created by Pelşin KAPLAN on 13.06.2022.
 */

@Dao
interface ChartDao {
    @Insert
    fun insert(product: ProductChart)

    @Query("SELECT * FROM chart_table")
    fun getAll(): List<ProductChart>

    @Update
    fun update(book: ProductChart)

    @Delete
    fun delete(book: ProductChart)

}