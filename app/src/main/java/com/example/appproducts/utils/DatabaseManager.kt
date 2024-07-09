package com.example.appproducts.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.appproducts.data.entities.Product

class DatabaseManager(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "products.db"
        const val DATABASE_VERSION = 1
        const val COLUMN_NAME_ID = "id"

        private const val SQL_CREATE_TABLE_PRODUCTS =
            "CREATE TABLE ${Product.TABLE_NAME} (" +
                    "$COLUMN_NAME_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${Product.COLUMN_TITLE} TEXT," +
                    "${Product.COLUMN_DESCRIPTION} TEXT," +
                    "${Product.COLUMN_CATEGORY} TEXT," +
                    "${Product.COLUMN_PRICE} REAL," +
                    "${Product.COLUMN_DISCOUNTPERCENTAGE} INTEGER," +
                    "${Product.COLUMN_RATING} INTEGER," +
                    "${Product.COLUMN_STOCK} TEXT," +
                    "${Product.COLUMN_IMAGE} TEXT)"


        private const val SQL_DELETE_TABLE_PRODUCTS = "DROP TABLE IF EXISTS ${Product.TABLE_NAME}"
    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
        //db?.execSQL("PRAGMA foreign_keys = ON;");
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_PRODUCTS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        destroyDatabase(db)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
    private fun destroyDatabase (db: SQLiteDatabase) {
        db.execSQL(SQL_DELETE_TABLE_PRODUCTS)
    }
}