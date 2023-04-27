package com.example.freshegokidproject.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.freshegokidproject.R
import com.example.freshegokidproject.model.Product
import com.squareup.picasso.Picasso

class ProductRecyclerViewAdapter(private val context: Context, private val products: List<Product>) : RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewHolder>() {
    var mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.recyclerview_product_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.configureProductView()
        val productPriceWithCurrency = context.resources.getString(R.string.product_price, product.price)
        holder.populateProductView(product, productPriceWithCurrency)
//        holder.itemView.setOnClickListener(ProductRecyclerOnItemTouchListener(context, product))
    }

    override fun getItemCount(): Int {
        return products.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var mDescriptionTextView: TextView
        private lateinit var mPriceTextView: TextView
        private lateinit var mImageView: ImageView

        fun configureProductView() {
            mDescriptionTextView = itemView.findViewById<TextView>(R.id.mainpage_productdescription)
            mPriceTextView = itemView.findViewById<TextView>(R.id.mainpage_productprice)
            mImageView = itemView.findViewById<ImageView>(R.id.mainpage_productimage)
        }

        fun populateProductView(product: Product, priceWithCurrency: String) {
            mDescriptionTextView.text = product.title
            mPriceTextView.text = priceWithCurrency
            val productEnum = ImageResourceEnum.valueOf(product.key.toUpperCase())
            Picasso.get().load(productEnum.getResourceId()).fit().into(mImageView)
//            itemView.tag = product.key
        }
    }

}