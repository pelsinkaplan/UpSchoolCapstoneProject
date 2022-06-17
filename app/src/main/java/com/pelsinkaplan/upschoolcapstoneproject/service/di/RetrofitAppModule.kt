package com.pelsinkaplan.upschoolcapstoneproject.service.di

import com.pelsinkaplan.upschoolcapstoneproject.service.network.RetrofitAPI
import com.pelsinkaplan.upschoolcapstoneproject.service.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Pelşin KAPLAN on 13.06.2022.
 */

@Module
@InstallIn(SingletonComponent::class)
object RetrofitAppModule {

    @Provides
    fun provideOkhttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(1000, TimeUnit.SECONDS)
            .writeTimeout(1000, TimeUnit.SECONDS)
            .readTimeout(1000, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun injectRetrofitAPI(): RetrofitAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkhttp())
            .baseUrl(BASE_URL).build().create(RetrofitAPI::class.java)
    }
}