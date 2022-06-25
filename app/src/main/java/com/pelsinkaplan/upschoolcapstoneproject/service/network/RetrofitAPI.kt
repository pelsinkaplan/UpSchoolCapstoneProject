package com.pelsinkaplan.upschoolcapstoneproject.service.network


import com.pelsinkaplan.upschoolcapstoneproject.data.apimodel.Result
import com.pelsinkaplan.upschoolcapstoneproject.data.model.Product
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by Pelşin KAPLAN on 13.06.2022.
 */

interface RetrofitAPI {


    // Caner API

    @POST("get_products_by_user.php")
    @FormUrlEncoded
    suspend fun getProductsByUser(
        @Field("user") user: String
    ): Response<ArrayList<Product>>

    @POST("get_products_by_user_and_category.php")
    @FormUrlEncoded
    suspend fun getProductsByUserAndCategory(
        @Field("user") user: String,
        @Field("category") category: String
    ): Response<ArrayList<Product>>

    @POST("get_sale_products_by_user.php")
    @FormUrlEncoded
    suspend fun getSaleProductsByUser(
        @Field("user") user: String,
    ): Response<ArrayList<Product>>

    @POST("add_to_bag.php")
    @FormUrlEncoded
    suspend fun addToBag(
        @Field("user") user: String,
        @Field("title") title: String,
        @Field("price") price: Double,
        @Field("description") description: String,
        @Field("category") category: String,
        @Field("image") image: String,
        @Field("rate") rate: Double,
        @Field("count") count: Int,
        @Field("sale_state") sale_state: Int,
    ): Result

    @POST("get_bag_products_by_user.php")
    @FormUrlEncoded
    suspend fun getBagProductsByUser(
        @Field("user") user: String,
    ): Response<List<Product>>

    @POST("delete_from_bag.php")
    @FormUrlEncoded
    suspend fun deleteFromBag(
        @Field("id") id: Int,
    ): Result

    @POST("get_categories_by_user.php")
    @FormUrlEncoded
    suspend fun getCategoriesByUser(@Field("user") user: String): Response<ArrayList<String>>

    @POST("get_products_by_user.php")
    @FormUrlEncoded
    suspend fun getProductsByUser(
        @Field("user") user: String, @Field("sort") sort: String, @Field("limit") limit: String
    ): Response<ArrayList<Product>>

}
