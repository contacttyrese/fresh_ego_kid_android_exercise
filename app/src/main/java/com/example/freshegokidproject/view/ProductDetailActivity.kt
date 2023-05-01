package com.example.freshegokidproject.view

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.freshegokidproject.databinding.ActivityProductDetailBinding
import com.example.freshegokidproject.viewmodel.DetailsUserAction
import com.example.freshegokidproject.viewmodel.DetailsViewModel
import com.example.freshegokidproject.viewmodel.DetailsViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailActivity : AppCompatActivity() {
    val viewModel: DetailsViewModel by viewModels()
    private var _imageUrl: String = ""
    private var detailsUrl: String = ""
    private var _title: String = ""
    private var _description: String = ""
    private var _price: String = ""
    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        intent.extras?.let { extras ->
            Log.i("activity_detail", "attempting to get extras from intent")
            _imageUrl = extras.getString("imageUrl")!!
            detailsUrl = extras.getString("detailsUrl")!!
        } ?: kotlin.run {
            RuntimeException("extras should not be null")
        }

        viewModel.setRequiredProductDetails(detailsUrl)

        populateView()
        createObservation()
    }

    override fun onStart() {
        super.onStart()
        viewModel.userActionSubject.onNext(DetailsUserAction.AwaitingSelection)
    }

    private fun createObservation() {
        viewModel.viewState.observe(this) { state ->
            when(state) {
                is DetailsViewState.Loading -> {
                    ProgressBar.VISIBLE
                    Log.i("details_loading", "details loading from viewstate")
                }
                is DetailsViewState.DetailsLoaded -> {
                    Log.i("details_loaded", "details loaded from viewstate")
                    ProgressBar.GONE
                    state.page.details.title?.let { title ->
                        _title = title
//                        binding.detailsTitle.text = _title
                    }

                    state.page.details.description?.let { description ->
                        _description = description
//                        binding.detailsDescription.text = _description
                    }

                    state.page.details.images?.let { images ->
                        images[0].src?.let { imageUrl ->
                            _imageUrl = imageUrl
//                            Glide.with(this).load(imageUrl).into(binding.detailsImage)
                        }
                    }

                    state.page.details.variants?.let { variants ->
                        variants[0].price?.let { price ->
                            _price = price
                            Log.i("price_updated", "price is $_price")
                        }
                    }
                    populateView()
                }
                is DetailsViewState.LoadingError -> {
                    ProgressBar.GONE
                }
            }
        }
    }

    private fun populateView() = with(binding) {
        Log.i("activity_create_view", "attempting to create view")
        detailsTitle.text = _title
        detailsDescription.text = _description
        Glide.with(this.root).load(_imageUrl).into(detailsImage)
    }

}

