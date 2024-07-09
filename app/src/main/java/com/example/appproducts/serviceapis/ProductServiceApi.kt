package com.example.appproducts.data.serviceapis

import com.example.appproducts.data.entities.Product
import com.example.appproducts.serviceapis.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductServiceApi {
    @GET("products/?limit=0")
    suspend fun findAll() : Response<ProductResponse>

    @GET("products/search")
    suspend fun findAllByName(@Query("q") query:String) : Response<ProductResponse>

    @GET("products/{id}")
    suspend fun findById(@Path("id") id:Int) : Response<Product>
}