package com.example.appproducts.activities

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import com.example.appproducts.R
import com.example.appproducts.data.daos.ProductDAO
import com.example.appproducts.data.entities.Product
import com.example.appproducts.data.serviceapis.ProductServiceApi
import com.example.appproducts.databinding.ActivityDetailBinding
import com.example.appproducts.utils.RetrofitProvider
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "PRODUCT_ID"
    }


    private lateinit var binding: ActivityDetailBinding

    private var productId:Int = -1
    private lateinit var product: Product

    private lateinit var productDAO: ProductDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productId = intent.getIntExtra(EXTRA_ID, -1)

        //setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.title = ""//name
        productDAO = ProductDAO(this)

        product = productDAO.find(productId)
        supportActionBar?.title = product.title//name
        loadData()
        //findRecipeById(recipeId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadData() {
        Picasso.get().load(product.image[0]).into(binding.photoImageView)
        binding.tittleTextView.text = product.title
        binding.descriptionTextView.text = product.description
        binding.categoryTextView.text = product.category
        binding.ratingTextView.text = product.rating.toString()
        binding.priceTextView.text = product.price.toString()
        binding.discountPercentageTextView.text = product.discountPercentage.toString()
        binding.stockTextView.text = product.stock

        val difficultyColor = when(product.stock.toFloat()) {
            in 1f..5f     -> R.color.hard_color
            in 5f..10f    -> R.color.medium_color
            !in 1f.. 10f  -> R.color.easy_color

            else -> R.color.grey
        }
        binding.stockTextView.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(binding.stockTextView.context, difficultyColor))

    }

    /*private fun findRecipeById(id: Int) {
        //binding.content.progress.visibility = View.VISIBLE

        val service: RecipeServiceApi = RetrofitProvider.getRecipeServiceApi()

        CoroutineScope(Dispatchers.IO).launch {
            // Llamada en segundo plano
            val response = service.findById(id)

            runOnUiThread {
                // Modificar UI
                //binding.content.progress.visibility = View.GONE
                if (response.body() != null) {
                    Log.i("HTTP", "respuesta correcta :)")
                    recipe = response.body()!!
                    loadData()
                } else {
                    Log.i("HTTP", "respuesta erronea :(")
                }
            }
        }
    }*/
}