package com.example.freshegokidproject.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.freshegokidproject.R
import com.example.freshegokidproject.databinding.ActivityMainBinding
import com.example.freshegokidproject.helpers.UiTestHelper
import com.example.freshegokidproject.model.SearchResult
import com.example.freshegokidproject.viewmodel.MainViewModel
import com.example.freshegokidproject.viewmodel.MainViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private var _bannerUrl = ""
    private var _products = ArrayList<SearchResult>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        createRecycler()
        createObservation()

        binding.homeTopNavigationSearchButton.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            R.id.NO_DEBUG
            startActivity(intent)
        }
    }

    private fun createObservation() {
        viewModel.viewState.observe(this) { state ->
            when (state) {
                MainViewState.LoadingBannerAndProducts -> {
                    ProgressBar.VISIBLE
                    Log.i("main_loading", "loading banner and products")
                }
                is MainViewState.LoadingError -> {
                    ProgressBar.GONE
                    Log.e("main_loading_error", "failed to load banner and products")
                }
                is MainViewState.LoadingSuccess -> {
                    ProgressBar.GONE
                    Log.i("main_loaded_success", "data loaded successfully")
                    _bannerUrl = state.bannerUrl
                    _products.removeAll(state.products.toSet())
                    _products.addAll(state.products)
                    binding.homeRecyclerView.adapter?.notifyDataSetChanged()
                    populateView()
                }
            }
        }
    }

    private fun populateView() = with(binding) {
        Glide.with(root).load(_bannerUrl).into(homeDiscountBanner)
    }

    private fun createRecycler() {
        if (UiTestHelper().isRunningTest()) {
            val testTitle = "this is a test title"
            val testPrice = "£9.99"
            val testImageUrl = "https://cdn.shopify.com/s/files/1/2579/8156/products/81freshegokidpeachbuckethat_360x.jpg"
            val testDetailsUrl = "https://www.freshegokid.com/products/new-era-bel-air-bucket-hat-peach"
            _products.add(SearchResult(title = testTitle, price = testPrice, imageUrl = testImageUrl, detailsUrl = testDetailsUrl))
        }
        val layoutManager = LinearLayoutManager(this)
        binding.homeRecyclerView.setHasFixedSize(true)
        binding.homeRecyclerView.setItemViewCacheSize(3)
        binding.homeRecyclerView.isNestedScrollingEnabled = true
        binding.homeRecyclerView.layoutManager = layoutManager
        binding.homeRecyclerView.adapter = SearchResultViewAdapter(_products)
        binding.homeRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                layoutManager.orientation
            )
        )
        binding.homeRecyclerView.findViewHolderForItemId(0)
    }

}