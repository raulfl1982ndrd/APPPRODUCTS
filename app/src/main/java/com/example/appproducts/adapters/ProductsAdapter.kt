package com.example.appproducts.adapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.appproducts.data.entities.Product
import com.example.appproducts.R
import com.example.appproducts.databinding.ItemProductBinding
import com.squareup.picasso.Picasso

class ProductsAdapter (var items:List<Product> = listOf(),
                      private val onClickListener: (position:Int) -> Unit
) : RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.render(items[position])
        holder.itemView.setOnClickListener { onClickListener(position) }
    }

    fun updateItems(results: List<Product>) {
        items = results
        notifyDataSetChanged()
    }
}

class ProductViewHolder(val binding:ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

    fun render(product: Product ) {
        binding.tittleTextView.text = product.title

        binding.categoryTextView.text = product.category
        binding.priceTextView.text = product.price.toString()
        binding.discountPercentageTextView.text = product.discountPercentage.toString()
        binding.ratingTextView.text = product.rating.toString()
        binding.stockTextView.text = product.stock

        val difficultyColor = when(product.stock.toFloat()) {
            in 1f..5f     -> R.color.hard_color
            in 5f..10f    -> R.color.medium_color
            !in 1f.. 10f  -> R.color.easy_color

            else -> R.color.grey
        }
        binding.stockTextView.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(binding.stockTextView.context, difficultyColor))
        Picasso.get().load(product.image[0]).into(binding.photoImageView)
    }

}