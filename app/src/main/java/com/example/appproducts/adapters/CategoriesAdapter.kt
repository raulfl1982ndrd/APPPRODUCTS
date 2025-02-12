package com.example.appproducts.adapters

import android.content.res.ColorStateList
import android.content.res.Resources
import android.content.res.loader.ResourcesLoader
import android.content.res.loader.ResourcesProvider
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.appproducts.data.entities.Product
import com.example.appproducts.R
import com.example.appproducts.data.daos.ProductDAO
import com.example.appproducts.databinding.ItemCategoryBinding
import com.squareup.picasso.Picasso

class CategoriesAdapter (var items:List<String> = listOf(),
                       private val onClickListener: (position:Int) -> Unit
) : RecyclerView.Adapter<CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.render(items[position])
        holder.itemView.setOnClickListener { onClickListener(position) }
    }

    fun updateItems(results: List<String>) {
        items = results
        notifyDataSetChanged()
    }
}

class CategoryViewHolder(val binding:ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

    fun render(string: String ) {
        binding.imageCategoryImageView.setImageResource(R.drawable.ic_rating_star)
        Picasso.get().load(ProductDAO(itemView.context).findCategoryImage(string)).into(binding.imageCategoryImageView)
        binding.categoryTextView.text = string.uppercase()
    }

}