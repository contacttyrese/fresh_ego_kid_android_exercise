package com.example.freshegokidproject.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.freshegokidproject.databinding.RecyclerviewProductListBinding
import com.example.freshegokidproject.model.ProductSearchResult

class ProductSearchResultViewAdapter(private val context: Context, private val products: List<ProductSearchResult>): RecyclerView.Adapter<ProductSearchResultViewAdapter.ViewHolder>() {
//    private var mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = mInflater.inflate(R.layout.recyclerview_product_list, parent, false)
        val binding = RecyclerviewProductListBinding.inflate(
            LayoutInflater.from(parent.context)
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
//        holder.configureProductView()
//        val productPriceWithCurrency = context.resources.getString(R.string.product_price, product.price)
//        holder.populateProductView(product, productPriceWithCurrency)
//        holder.itemView.setOnClickListener(ProductRecyclerOnItemTouchListener(context, product))
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return products.size
    }

//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    class ViewHolder(private val binding: RecyclerviewProductListBinding) : RecyclerView.ViewHolder(binding.root) {
//        private lateinit var mDescriptionTextView: TextView
//        private lateinit var mPriceTextView: TextView
//        private lateinit var mImageView: ImageView

        fun bind(searchResult: ProductSearchResult) = with(binding) {
            Glide.with(root).load(searchResult.imageUrl).into(mainpageProductimage)
            mainpageProductprice.text = searchResult.price
            mainpageProductdescription.text = searchResult.title
        }
//
//        fun configureProductView() {
//            mDescriptionTextView = itemView.findViewById<TextView>(R.id.mainpage_productdescription)
//            mPriceTextView = itemView.findViewById<TextView>(R.id.mainpage_productprice)
//            mImageView = itemView.findViewById<ImageView>(R.id.mainpage_productimage)
//        }
//
//        fun populateProductView(product: Product, priceWithCurrency: String) {
//            mDescriptionTextView.text = product.title
//            mPriceTextView.text = product.price
//            Glide.with(itemView).load(product.imageUrl).into(mImageView)
//            val productEnum = ImageResourceEnum.valueOf(product.key.toUpperCase())
//            Picasso.get().load(productEnum.getResourceId()).fit().into(mImageView)
//            itemView.tag = product.key
//        }
    }
}