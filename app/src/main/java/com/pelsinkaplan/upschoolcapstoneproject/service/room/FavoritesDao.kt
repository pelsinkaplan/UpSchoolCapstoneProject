package com.pelsinkaplan.upschoolcapstoneproject.service.room

import androidx.room.*
import com.pelsinkaplan.upschoolcapstoneproject.data.model.Product


/**
 * Created by Pelşin KAPLAN on 19.06.2022.
 */
@Dao
interface FavoritesDao {
    @Insert
    fun insert(product: Product)

    @Query("SELECT * FROM favorite_product_table")
    fun getAll(): List<Product>

    @Update
    fun update(book: Product)

    @Delete
    fun delete(book: Product)

    @Query("DELETE FROM favorite_product_table")
    fun deleteAll()

}