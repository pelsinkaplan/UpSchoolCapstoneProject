package com.pelsinkaplan.upschoolcapstoneproject.service.network


import com.pelsinkaplan.upschoolcapstoneproject.data.model.Product
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by Pelşin KAPLAN on 13.06.2022.
 */

interface RetrofitAPI {

    @GET("products")
    suspend fun getProductList(): Response<ArrayList<Product>>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: String): Response<Product>

    @GET("products")
    suspend fun getProductList(@Query("limit") limit: Int): Response<ArrayList<Product>>

    @GET("products/categories")
    suspend fun getCategories(): Response<List<String>>

    @GET("products/category/{category}")
    suspend fun getCategoryProducts(@Path("category") category: String): Response<ArrayList<Product>>
}