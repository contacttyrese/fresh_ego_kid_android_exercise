package com.example.freshegokidproject.view

import android.content.Intent
import android.view.View
import com.example.freshegokidproject.BaseApplication
import com.example.freshegokidproject.model.Product
import javax.inject.Inject

class ProductRecyclerOnItemTouchListener @Inject constructor(
    val context: BaseApplication,
    val product: Product) : View.OnClickListener {
    override fun onClick(v: View?) {
        val intent = Intent(context, ProductDetailActivity::class.java)
        intent.putExtra("productToDisplay", product)
        context.startActivity(intent)
    }
}