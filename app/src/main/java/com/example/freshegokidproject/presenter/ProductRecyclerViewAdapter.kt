package com.example.freshegokidproject.presenter

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.freshegokidproject.R
import com.example.freshegokidproject.model.Product

class ProductRecyclerViewAdapter(private val context: Context, private val products: List<Product>) : RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewHolder>() {
    var mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products.get(position)
        holder.mDescriptionTextView.text = product.description
        holder.mPriceTextView.text = product.price
        holder.mImageView.setImageDrawable(product.imageDrawable)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.mainpage_product_list, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mDescriptionTextView: TextView
        val mPriceTextView: TextView
        val mImageView: ImageView

        init {
            mDescriptionTextView = itemView.findViewById<TextView>(R.id.mainpage_productdescription)
            mPriceTextView = itemView.findViewById<TextView>(R.id.mainpage_productprice)
            mImageView = itemView.findViewById<ImageView>(R.id.mainpage_productimage)
        }

    }

}