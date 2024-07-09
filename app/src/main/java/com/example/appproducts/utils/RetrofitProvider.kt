package com.example.appproducts.utils

import com.example.appproducts.data.serviceapis.ProductServiceApi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvider {
    companion object {
        fun getProductServiceApi(): ProductServiceApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ProductServiceApi::class.java)
        }
    }
}