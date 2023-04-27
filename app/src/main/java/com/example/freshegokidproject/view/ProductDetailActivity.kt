package com.example.freshegokidproject.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.freshegokidproject.databinding.ActivityProductDetailBinding
import com.example.freshegokidproject.model.Product
import com.squareup.picasso.Picasso

class ProductDetailActivity : AppCompatActivity() {
    var product: Product? = null
    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        product = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.extras?.getParcelable("productToDisplay", Product::class.java)
        } else {
            intent.extras?.getParcelable("productToDisplay")
        }

        product?.let {
            val productEnum = ImageResourceEnum.valueOf(it.key.toUpperCase())
            Picasso.get().load(productEnum.getResourceId()).into(binding.productDetailImage)
            binding.productDetailTitle.text = it.title
            binding.detailactDescription.text = it.description

            binding.productDetailHomeButton.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        } ?: kotlin.run {
            println("product was null")
        }

    }

}