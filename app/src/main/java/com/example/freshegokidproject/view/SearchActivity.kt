package com.example.freshegokidproject.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.freshegokidproject.databinding.ActivitySearchBinding
import com.example.freshegokidproject.model.Product
import com.example.freshegokidproject.model.ProductSearchResult
import com.example.freshegokidproject.viewmodel.SearchUserAction
import com.example.freshegokidproject.viewmodel.SearchViewModel
import com.example.freshegokidproject.viewmodel.SearchViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
//    private lateinit var viewModel: SearchViewModel
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var _products: ArrayList<Product>
    private val _searchResults = ArrayList<ProductSearchResult>()
    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

//        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        createRecycler()
        createObservation()

//        binding.searchRecyclerView.adapter = null

//        _products = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            intent.getParcelableArrayListExtra("products", Product::class.java) as ArrayList<Product>
//        } else {
//            intent.getParcelableArrayListExtra<Product>("products") as ArrayList<Product>
//        }

        binding.searchHomeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.searchView.setOnQueryTextListener(this)
        binding.searchView.isIconifiedByDefault = false

        // TODO: Modify results to populate view recycler view
//        viewModel.searchForProductsWithQueryViaHtmlService("grey")
    }

    override fun onStart() {
        super.onStart()
        Log.i("start", "start activity")
        viewModel.userActionSubject.onNext(SearchUserAction.AwaitingInput)
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let { input ->
            viewModel.userActionSubject.onNext(SearchUserAction.UserTyping(input))
        } ?: kotlin.run {
            Log.i("typing", "input is null")
        }
        return false
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { query ->
            viewModel.userActionSubject.onNext(SearchUserAction.QuerySubmittedSuccess(query))
//            viewModel.userAction.postValue(SearchUserAction.QuerySubmittedSuccess(query))
//            productsFound = viewModel.getProductsFoundByQuery(query, products)
//            val layoutManager = LinearLayoutManager(this)
//            binding.searchRecyclerView.setItemViewCacheSize(3)
//            binding.searchRecyclerView.isNestedScrollingEnabled = true
//            binding.searchRecyclerView.layoutManager = layoutManager
//            binding.searchRecyclerView.adapter = ProductRecyclerViewAdapter(this, productsFound)
//            binding.searchRecyclerView.adapter = ProductSearchResultViewAdapter(this, productsFound)
//            binding.searchRecyclerView.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))
            return false
        } ?: kotlin.run {
            println("query was null")
            viewModel.userAction.postValue(SearchUserAction.QuerySubmittedError)
            return false
        }
    }

    private fun createObservation() {
        viewModel.viewState.observe(this) { state ->
            when (state) {
                SearchViewState.Loading -> {
                    ProgressBar.VISIBLE
                    Log.i("observe_loading", "loading product")
                }
                is SearchViewState.ProductLoadError -> {
                    ProgressBar.GONE
                    Log.e("observe_error", "product loading error ${state.throwable}")
                }
                is SearchViewState.ProductLoaded -> {
                    ProgressBar.GONE
                    _searchResults.removeAll(_searchResults.toSet())
                    _searchResults.addAll(state.searchResults.productSearchResults)
                    binding.searchRecyclerView.adapter?.let {
                        it.notifyDataSetChanged()
                        Log.i("recycler_update_success", "sent notification of data change in recylcer")
                    } ?: kotlin.run {
                        Log.e("recycler_update_fail", "recycler was null so notification not sent")
                    }
                    Log.i("observe_loaded", "products loaded")
                }
            }
        }

//        viewModel.viewState.observe(this) { state ->
//            when (state) {
//                SearchViewState.Loading -> {
//                    ProgressBar.VISIBLE
//                    Log.i("activity_search", "products loading")
//                }
//                is SearchViewState.ProductLoadError -> {
//                    ProgressBar.GONE
//                    Log.e("activity_search_error", "products loading error: ${state.throwable}")
//                }
//                is SearchViewState.ProductLoaded -> {
//                    ProgressBar.GONE
//                    Log.i("activity_search_success", "products loaded successfully")
//                }
//            }
//        }
    }

    private fun createRecycler() {
//        binding.searchRecyclerView.adapter = null
        binding.searchRecyclerView.adapter = ProductSearchResultViewAdapter(this, _searchResults)
        val layoutManager = LinearLayoutManager(this)
        binding.searchRecyclerView.layoutManager = layoutManager
        binding.searchRecyclerView.isNestedScrollingEnabled = true
        binding.searchRecyclerView.setItemViewCacheSize(3)
//            binding.searchRecyclerView.adapter = ProductRecyclerViewAdapter(this, productsFound)
//        binding.searchRecyclerView.adapter = ProductSearchResultViewAdapter(this, _products)
        binding.searchRecyclerView.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearDisposable()
    }
}