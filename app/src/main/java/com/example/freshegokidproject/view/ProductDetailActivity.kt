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
import com.example.freshegokidproject.model.Product
import com.example.freshegokidproject.viewmodel.ImageResourceEnum
import com.squareup.picasso.Picasso

class ProductDetailActivity : AppCompatActivity() {
    lateinit var homeButton: Button
    lateinit var imageView: ImageView
    lateinit var titleView: TextView
    lateinit var descriptionView: TextView
    var product: Product? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        homeButton = findViewById<Button>(R.id.detailact_home_button)
        imageView = findViewById<ImageView>(R.id.detailact_image)
        titleView = findViewById<TextView>(R.id.detailact_title)
        descriptionView = findViewById<TextView>(R.id.detailact_description)

        product = intent.extras?.getParcelable("productToDisplay", Product::class.java)!!
        assert(product != null) { "product is empty" }

        val productEnum = ImageResourceEnum.valueOf(product!!.key.toUpperCase())
        Picasso.get().load(productEnum.getResourceId()).into(imageView)
        titleView.text = product!!.title
        descriptionView.text = product!!.description

        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}