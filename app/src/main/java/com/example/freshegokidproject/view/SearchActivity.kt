package com.example.freshegokidproject.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.freshegokidproject.databinding.ActivitySearchBinding
import com.example.freshegokidproject.model.Product
import com.example.freshegokidproject.viewmodel.ProductRecyclerViewAdapter
import com.example.freshegokidproject.viewmodel.SearchViewModel

class SearchActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var viewModel: SearchViewModel
    private lateinit var products: ArrayList<Product>
    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        products = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableArrayListExtra("products", Product::class.java) as ArrayList<Product>
        } else {
            intent.getParcelableArrayListExtra<Product>("products") as ArrayList<Product>
        }

        binding.searchHomeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.searchView.setOnQueryTextListener(this)
        binding.searchView.isIconifiedByDefault = false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            var productsFound : ArrayList<Product> = viewModel.getProductsFoundByQuery(query, products)
            val layoutManager = LinearLayoutManager(this)
            binding.searchRecyclerView.setItemViewCacheSize(3)
            binding.searchRecyclerView.isNestedScrollingEnabled = true
            binding.searchRecyclerView.layoutManager = layoutManager
            binding.searchRecyclerView.adapter = ProductRecyclerViewAdapter(this, productsFound)
            binding.searchRecyclerView.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))
            return false
        } ?: kotlin.run {
            println("query was null")
            return false
        }
    }
}