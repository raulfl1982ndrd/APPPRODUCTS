package com.example.appproducts.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.appproducts.data.daos.ProductDAO
import com.example.appproducts.data.serviceapis.ProductServiceApi
import com.example.appproducts.databinding.ActivityMainBinding
import com.example.appproducts.utils.RetrofitProvider
import com.example.recipes.utils.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        session = SessionManager(this)
        if (session.didFetchData) {
            navigateToProductList()
        } else {
            getAllProducts()
        }
        binding.recargarDatos.setOnClickListener(){
            getAllProducts()
        }
        binding.noRecargarDatos.setOnClickListener(){
            navigateToProductList()
        }
    }

    private fun getAllProducts() {
        val service: ProductServiceApi = RetrofitProvider.getProductServiceApi()

        CoroutineScope(Dispatchers.IO).launch {
            // Llamada en segundo plano
            val response = service.findAll()

            runOnUiThread {
                // Modificar UI
                if (response.body() != null) {
                    Log.i("HTTP", "respuesta correcta :)")
                    var productsList = response.body()?.results.orEmpty()

                    val productDAO = ProductDAO(this@MainActivity)
                    productDAO.deleteAll()
                    for (product in productsList) {
                        productDAO.insert(product)
                    }

                    session.didFetchData = true
                    navigateToProductList()
                } else {
                    Log.i("HTTP", "respuesta erronea :(")
                    Toast.makeText(this@MainActivity, "Hubo un error inesperado, vuelva a intentarlo más tarde", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun navigateToProductList() {
        val intent = Intent(this, CategoriesActivity::class.java)
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        //finish()
    }
}