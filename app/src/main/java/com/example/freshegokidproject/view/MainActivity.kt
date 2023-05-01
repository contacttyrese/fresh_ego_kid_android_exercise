package com.example.freshegokidproject.view

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.freshegokidproject.databinding.ActivityMainBinding
import com.example.freshegokidproject.viewmodel.ProductViewModel
import com.example.freshegokidproject.viewmodel.ProductViewModelFactory
import java.io.BufferedReader

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ProductViewModel
    private lateinit var reader: BufferedReader
    private lateinit var viewModelFactory : ProductViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        reader = assets.open("first.json").bufferedReader(Charsets.UTF_8)
        viewModelFactory = ProductViewModelFactory(reader)
        viewModel = ViewModelProvider(this, viewModelFactory)[ProductViewModel::class.java]

        val products = viewModel.getAllProducts()
        val layoutManager = LinearLayoutManager(this)

        binding.homeProductList.setHasFixedSize(true)
        binding.homeProductList.setItemViewCacheSize(3)
        binding.homeProductList.isNestedScrollingEnabled = true
        binding.homeProductList.layoutManager = layoutManager
        binding.homeProductList.adapter = ProductRecyclerViewAdapter(this, products)
        binding.homeProductList.addItemDecoration(
            DividerItemDecoration(
                this,
                layoutManager.orientation
            )
        )
        binding.homeProductList.findViewHolderForItemId(1)

        binding.searchButton.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            intent.putParcelableArrayListExtra("products", products)
            startActivity(intent)
        }
    }

}