package com.example.appproducts.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appproducts.data.daos.ProductDAO
import com.example.appproducts.data.entities.Product
import com.example.appproducts.databinding.ActivityProductsBinding
import com.example.appproducts.R
import com.example.appproducts.adapters.ProductsAdapter

class ProductsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductsBinding

    private lateinit var adapter: ProductsAdapter
    private var productsList:List<Product> = listOf()

    private lateinit var productDAO: ProductDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productDAO = ProductDAO(this)

        initView()
    }

    private fun initView() {
        adapter = ProductsAdapter() {
            onItemClickListener(it)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        productsList = productDAO.findAll()
        if (productsList.isEmpty()) {
            binding.recyclerView.visibility = View.GONE
            binding.emptyPlaceholder.visibility = View.VISIBLE
        } else {
            adapter.updateItems(productsList)
            binding.recyclerView.visibility = View.VISIBLE
            binding.emptyPlaceholder.visibility = View.GONE
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.products_menu, menu)

        initSearchView(menu?.findItem(R.id.menu_search))

        return true
    }

    private fun initSearchView(searchItem: MenuItem?) {
        if (searchItem != null) {
            var searchView = searchItem.actionView as SearchView

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    searchView.clearFocus()
                    return false
                }

                override fun onQueryTextChange(query: String): Boolean {
                    searchAllProductsByName(query)
                    return true
                }
            })
        }
    }

    private fun searchAllProductsByName(query: String) {
        val searchList = productsList.filter { it.title.contains(query, true) }
        adapter.updateItems(searchList)
    }

    private fun onItemClickListener(position:Int) {
        val product: Product = adapter.items[position]

        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_ID, product.id)
        startActivity(intent)
        //Toast.makeText(this, getString(products.name), Toast.LENGTH_LONG).show()
    }

    /*private fun getAllRecipes() {
        binding.progress.visibility = View.VISIBLE

        val service: RecipeServiceApi = RetrofitProvider.getRecipeServiceApi()

        CoroutineScope(Dispatchers.IO).launch {
            // Llamada en segundo plano
            val response = service.findAll()

            runOnUiThread {
                // Modificar UI
                binding.progress.visibility = View.GONE

                if (response.body() != null) {
                    Log.i("HTTP", "respuesta correcta :)")
                    recipesList = response.body()?.results.orEmpty()
                    adapter.updateItems(recipesList)

                    if (recipesList.isNotEmpty()) {
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.emptyPlaceholder.visibility = View.GONE
                    } else {
                        binding.recyclerView.visibility = View.GONE
                        binding.emptyPlaceholder.visibility = View.VISIBLE
                    }
                } else {
                    Log.i("HTTP", "respuesta erronea :(")
                    Toast.makeText(this@RecipesActivity, "Hubo un error inesperado, vuelva a intentarlo más tarde", Toast.LENGTH_LONG).show()
                }
            }
        }
    }*/
}