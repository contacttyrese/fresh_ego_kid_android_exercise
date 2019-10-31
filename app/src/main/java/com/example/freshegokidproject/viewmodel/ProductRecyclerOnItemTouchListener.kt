package com.example.freshegokidproject.viewmodel

import android.content.Context
import android.content.Intent
import android.view.View
import com.example.freshegokidproject.model.Product
import com.example.freshegokidproject.view.ProductDetailActivity

class ProductRecyclerOnItemTouchListener(val context: Context, val product: Product) : View.OnClickListener {
    override fun onClick(v: View?) {
        val intent = Intent(context, ProductDetailActivity::class.java)
        intent.putExtra("productToDisplay", product)
        context.startActivity(intent)
    }
}