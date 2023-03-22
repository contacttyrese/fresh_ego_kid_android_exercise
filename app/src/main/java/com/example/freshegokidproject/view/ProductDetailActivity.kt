package com.example.freshegokidproject.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.freshegokidproject.R
import com.example.freshegokidproject.databinding.ActivityProductDetailBinding
import com.example.freshegokidproject.model.Product
import com.example.freshegokidproject.viewmodel.ImageResourceEnum
import com.squareup.picasso.Picasso

class ProductDetailActivity : AppCompatActivity() {
    var product: Product? = null
    private lateinit var binding: ActivityProductDetailBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        product = intent.extras?.getParcelable("productToDisplay", Product::class.java)!!
        assert(product != null) { "product is empty" }

        val productEnum = ImageResourceEnum.valueOf(product!!.key.toUpperCase())
        Picasso.get().load(productEnum.getResourceId()).into(binding.productDetailImage)
        binding.productDetailTitle.text = product!!.title
        binding.detailactDescription.text = product!!.description

        binding.productDetailHomeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}