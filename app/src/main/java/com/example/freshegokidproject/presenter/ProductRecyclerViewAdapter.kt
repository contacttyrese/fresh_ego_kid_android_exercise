package com.example.freshegokidproject.presenter

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
        val view = mInflater.inflate(R.layout.mainpage_product_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products.get(position)
        holder.populateProductView(product)
    }

    override fun getItemCount(): Int {
        return products.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var mDescriptionTextView: TextView
        lateinit var mPriceTextView: TextView
        lateinit var mImageView: ImageView

        fun populateProductView(product: Product) {
            mDescriptionTextView = itemView.findViewById<TextView>(R.id.mainpage_productdescription)
            mPriceTextView = itemView.findViewById<TextView>(R.id.mainpage_productprice)
            mImageView = itemView.findViewById<ImageView>(R.id.mainpage_productimage)

            mDescriptionTextView.text = product.description
            mPriceTextView.text = product.price
            val productEnum = ImageResourceEnum.valueOf(product.key.toUpperCase())
            Picasso.get().load(productEnum.getResourceId()).fit().into(mImageView)
        }
    }

}

private enum class ImageResourceEnum {
    FEK_460 {
        override fun getResourceId() = R.drawable.fek_460
    },
    FEK_497 {
        override fun getResourceId() = R.drawable.fek_497
    },
    FEK_500 {
        override fun getResourceId() = R.drawable.fek_500
    },
    FEK_10193 {
        override fun getResourceId() = R.drawable.fek_10193
    },
    FEK_10206 {
        override fun getResourceId() = R.drawable.fek_10206
    },
    FEK_10225 {
        override fun getResourceId() = R.drawable.fek_10225
    };

    abstract fun getResourceId() : Int
}