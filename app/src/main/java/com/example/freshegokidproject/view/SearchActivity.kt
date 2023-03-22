package com.example.freshegokidproject.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.freshegokidproject.R
import com.example.freshegokidproject.databinding.ActivitySearchBinding
import com.example.freshegokidproject.model.Product
import com.example.freshegokidproject.viewmodel.ProductRecyclerViewAdapter

class SearchActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    lateinit var searchRecyclerView: RecyclerView
    lateinit var products: List<Product>
    private lateinit var binding: ActivitySearchBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        products = intent.getParcelableArrayListExtra<Product>("products", Product::class.java)!!
        searchRecyclerView = findViewById<RecyclerView>(R.id.searchRecyclerView)

        binding.searchHomeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.searchView.setOnQueryTextListener(this)
        binding.searchView.setIconifiedByDefault(false)
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        var productsFound : ArrayList<Product> = ArrayList()
        for (product in products) {
            if (product.title.contains(query.toString(), true) ||
                    product.description.contains(query.toString(), true)) {
                productsFound.add(product)
            }
        }

        val layoutManager = LinearLayoutManager(this)
        searchRecyclerView.setItemViewCacheSize(3)
        searchRecyclerView.isNestedScrollingEnabled = true
        searchRecyclerView.layoutManager = layoutManager
        searchRecyclerView.adapter = ProductRecyclerViewAdapter(this, productsFound)
        searchRecyclerView.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))
        return false
    }
}