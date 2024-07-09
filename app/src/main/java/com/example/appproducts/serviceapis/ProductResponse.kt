package com.example.appproducts.serviceapis

import com.example.appproducts.data.entities.Product
import com.google.gson.annotations.SerializedName
class ProductResponse (
    @SerializedName("total") val total:String,
    @SerializedName("skip") val skip:String,
    @SerializedName("limit") val limit:String,
    @SerializedName("products") val results:List<Product>
) {

}