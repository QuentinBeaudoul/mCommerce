package com.beaudoul.mcommerce.adapter


import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.beaudoul.mcommerce.R
import com.beaudoul.mcommerce.databinding.ProductItemViewBinding
import com.beaudoul.mcommerce.model.Product
import com.bumptech.glide.Glide

class ProductAdapter : ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductsComparator()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProductViewHolder(private val binding: ProductItemViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product){
            binding.apply {
                priceTextView.text = product.price.toString()
                nameTextView.text = product.name


                Glide.with(itemView).load(product.image).placeholder(R.mipmap.ic_launcher).into(imageView)
            }
        }
    }

    class ProductsComparator : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }
    }
}