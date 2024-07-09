package com.example.appproducts.data.entities

import com.example.appproducts.utils.DatabaseManager
import com.google.gson.annotations.SerializedName

class Product (
    @SerializedName("id") var id:Int,
    @SerializedName("title") var title:String,
    @SerializedName("description") var description:String,
    @SerializedName("category") var category:String,
    @SerializedName("price") var price:Float,
    @SerializedName("discountPercentage") var discountPercentage:Float,
    @SerializedName("rating") var rating:Float,
    @SerializedName("stock") var stock:String,
    @SerializedName("images") var image:List<String>,
) {


    //Raul eres the best =) -> By Gemita
    companion object {
        const val TABLE_NAME = "Products"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_CATEGORY = "category"
        const val COLUMN_PRICE = "price"
        const val COLUMN_DISCOUNTPERCENTAGE = "discountPercentage"
        const val COLUMN_RATING = "rating"
        const val COLUMN_STOCK = "stock"
        const val COLUMN_IMAGE = "image"
        val COLUMN_NAMES = arrayOf(
            DatabaseManager.COLUMN_NAME_ID,
            COLUMN_TITLE,
            COLUMN_DESCRIPTION,
            COLUMN_CATEGORY,
            COLUMN_PRICE,
            COLUMN_DISCOUNTPERCENTAGE,
            COLUMN_RATING,
            COLUMN_STOCK,
            COLUMN_IMAGE
        )
    }
}