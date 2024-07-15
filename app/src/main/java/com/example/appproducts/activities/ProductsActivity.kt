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
    private var category:String? = ""
    private lateinit var productDAO: ProductDAO
    companion object {
        const val EXTRA_CATEGORY = "CATEGORY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        category = intent.getStringExtra(ProductsActivity.EXTRA_CATEGORY)
        productDAO = ProductDAO(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.title = category?.uppercase()//name

        initView()
    }

    private fun initView() {
        adapter = ProductsAdapter() {
            onItemClickListener(it)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        if (category.equals("Todas"))
            productsList = productDAO.findAll()
        else
            productsList = productDAO.findByCategory(category)

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
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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

}