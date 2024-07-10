package com.example.appproducts.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appproducts.R
import com.example.appproducts.adapters.CategoriesAdapter
import com.example.appproducts.data.daos.ProductDAO
import com.example.appproducts.data.entities.Product
import com.example.appproducts.databinding.ActivityCategoriesBinding

class CategoriesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoriesBinding
    private lateinit var productDAO: ProductDAO
    private lateinit var adapter: CategoriesAdapter
    private var categoriesList:List<String> = listOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productDAO = ProductDAO(this)

        //setSupportActionBar(binding.toolbar)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.title = "CATEGORIES"//name

        binding.allCategoriesButton.setOnClickListener(){

            val intent = Intent(this, ProductsActivity::class.java)
            intent.putExtra(ProductsActivity.EXTRA_CATEGORY, "Todas")
            startActivity(intent)
        }
        initView()
    }

    private fun initView() {
        adapter = CategoriesAdapter() {
            onItemClickListener(it)
        }
        binding.categoryRecyclerView.adapter = adapter
        binding.categoryRecyclerView.layoutManager = LinearLayoutManager(this)

        categoriesList = productDAO.findAllCategories()
        if (categoriesList.isEmpty()) {
            binding.categoryRecyclerView.visibility = View.GONE
            binding.emptyPlaceholder.visibility = View.VISIBLE
        } else {
            adapter.updateItems(categoriesList)
            binding.categoryRecyclerView.visibility = View.VISIBLE
            binding.emptyPlaceholder.visibility = View.GONE
        }

    }
    private fun onItemClickListener(position:Int) {
        val string: String = adapter.items[position]

        val intent = Intent(this, ProductsActivity::class.java)
        intent.putExtra(ProductsActivity.EXTRA_CATEGORY, string)
        startActivity(intent)
        //Toast.makeText(this, getString(products.name), Toast.LENGTH_LONG).show()
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
                    searchAllCategoriesByName(query)
                    return true
                }
            })
        }
    }

    private fun searchAllCategoriesByName(query: String) {
        val searchList = categoriesList.filter { it.contains(query, true) }
        adapter.updateItems(searchList)
    }

}