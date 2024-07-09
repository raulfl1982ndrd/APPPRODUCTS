package com.example.appproducts.data.daos

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.appproducts.data.entities.Product
import com.example.appproducts.utils.DatabaseManager



class ProductDAO (private val context: Context) {

    private var databaseManager: DatabaseManager = DatabaseManager(context)

    fun insert(product: Product): Product {
        val db = databaseManager.writableDatabase

        val values = ContentValues()

        values.put(Product.COLUMN_TITLE, product.title)
        values.put(Product.COLUMN_DESCRIPTION, product.description)
        values.put(Product.COLUMN_CATEGORY, product.category)
        values.put(Product.COLUMN_PRICE, product.price)
        values.put(Product.COLUMN_DISCOUNTPERCENTAGE, product.discountPercentage)
        values.put(Product.COLUMN_RATING, product.rating)
        values.put(Product.COLUMN_STOCK, product.stock)
        values.put(Product.COLUMN_IMAGE, product.image[0])


        val newRowId = db.insert(Product.TABLE_NAME, null, values)
        Log.i("DATABASE", "New record id: $newRowId")

        db.close()


        product.id = newRowId.toInt()
        return product
    }

    fun update(product: Product) {
        val db = databaseManager.writableDatabase

        val values = ContentValues()
        values.put(Product.COLUMN_TITLE, product.title)
        values.put(Product.COLUMN_DESCRIPTION, product.description)
        values.put(Product.COLUMN_CATEGORY, product.category)
        values.put(Product.COLUMN_PRICE, product.price)
        values.put(Product.COLUMN_DISCOUNTPERCENTAGE, product.discountPercentage)
        values.put(Product.COLUMN_RATING, product.rating)
        values.put(Product.COLUMN_STOCK, product.stock)
        values.put(Product.COLUMN_IMAGE, product.image[0])

        val updatedRows = db.update(Product.TABLE_NAME, values, "${DatabaseManager.COLUMN_NAME_ID} = ${product.id}", null)
        Log.i("DATABASE", "Updated records: $updatedRows")

        db.close()
    }

    fun delete(product: Product) {
        val db = databaseManager.writableDatabase

        val deletedRows = db.delete(Product.TABLE_NAME, "${DatabaseManager.COLUMN_NAME_ID} = ${product.id}", null)
        Log.i("DATABASE", "Deleted rows: $deletedRows")

        db.close()
    }

    fun deleteAll() {
        val db = databaseManager.writableDatabase

        val deletedRows = db.delete(Product.TABLE_NAME, null, null)
        Log.i("DATABASE", "Deleted rows: $deletedRows")

        db.close()
    }

    @SuppressLint("Range")
    fun find(id: Int): Product {
        val db = databaseManager.writableDatabase

        val cursor = db.query(
            Product.TABLE_NAME,                         // The table to query
            Product.COLUMN_NAMES,       // The array of columns to return (pass null to get all)
            "${DatabaseManager.COLUMN_NAME_ID} = $id",                        // The columns for the WHERE clause
            null,                    // The values for the WHERE clause
            null,                        // don't group the rows
            null,                         // don't filter by row groups
            null                         // The sort order
        )

        var product: Product? = null

        if (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(DatabaseManager.COLUMN_NAME_ID))
            val productTitle = cursor.getString(cursor.getColumnIndex(Product.COLUMN_TITLE))
            val productDescription = cursor.getString(cursor.getColumnIndex(Product.COLUMN_DESCRIPTION))
            val productCategory = cursor.getString(cursor.getColumnIndex(Product.COLUMN_CATEGORY))
            val productPrice = cursor.getFloat(cursor.getColumnIndex(Product.COLUMN_PRICE))
            val productDiscountPercentage = cursor.getFloat(cursor.getColumnIndex(Product.COLUMN_DISCOUNTPERCENTAGE))
            val productRating = cursor.getFloat(cursor.getColumnIndex(Product.COLUMN_RATING))
            val productStock = cursor.getString(cursor.getColumnIndex(Product.COLUMN_STOCK))
            val productImage = cursor.getString(cursor.getColumnIndex(Product.COLUMN_IMAGE))
            //Log.i("DATABASE", "$id -> Product: $productDescription, Image: productImage")

            product = Product(id,
                    productTitle,
                    productDescription,
                    productCategory,
                    productPrice,
                    productDiscountPercentage,
                    productRating ,
                    productStock,
                    listOf(productImage))
        }

        cursor.close()
        db.close()

        return product!!
    }

    @SuppressLint("Range")
    fun findAll(): List<Product> {
        val db = databaseManager.writableDatabase

        val cursor = db.query(
            Product.TABLE_NAME,                 // The table to query
            Product.COLUMN_NAMES,     // The array of columns to return (pass null to get all)
            null,                // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null               // The sort order
        )

        val list: MutableList<Product> = mutableListOf()

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(DatabaseManager.COLUMN_NAME_ID))
            val productTitle = cursor.getString(cursor.getColumnIndex(Product.COLUMN_TITLE))
            val productDescription = cursor.getString(cursor.getColumnIndex(Product.COLUMN_DESCRIPTION))
            val productCategory = cursor.getString(cursor.getColumnIndex(Product.COLUMN_CATEGORY))
            val productPrice = cursor.getFloat(cursor.getColumnIndex(Product.COLUMN_PRICE))
            val productDiscountPercentage = cursor.getFloat(cursor.getColumnIndex(Product.COLUMN_DISCOUNTPERCENTAGE))
            val productRating = cursor.getFloat(cursor.getColumnIndex(Product.COLUMN_RATING))
            val productStock = cursor.getString(cursor.getColumnIndex(Product.COLUMN_STOCK))
            val productImage = cursor.getString(cursor.getColumnIndex(Product.COLUMN_IMAGE))
            //Log.i("DATABASE", "$id -> Recipe: $recipeName, Done: $done")

            val product = Product(id,
                productTitle,
                productDescription,
                productCategory,
                productPrice,
                productDiscountPercentage,
                productRating ,
                productStock,
                listOf(productImage))
            list.add(product)
        }

        cursor.close()
        db.close()

        return list
    }
}