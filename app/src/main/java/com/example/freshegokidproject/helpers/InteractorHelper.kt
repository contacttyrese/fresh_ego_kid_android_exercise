package com.example.freshegokidproject.helpers

import android.util.Log
import com.example.freshegokidproject.model.SearchResult

class InteractorHelper {
    fun refactorImageUrlsForPage(searchResult: SearchResult) {
        searchResult.imageUrl?.let { imageUrl ->
            val refactoredUrl = imageUrl.replace("{width}", "360")
            Log.i("image_url_raw", "print image raw url: $imageUrl")
            searchResult.imageUrl = "https:$refactoredUrl"
            Log.i("image_url_refactored", "image refactored url: ${searchResult.imageUrl}")
        } ?: kotlin.run {
            Log.e("next_imageurl_error", "image url for search result is null")
        }
    }
}