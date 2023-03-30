package com.example.freshegokidproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.io.BufferedReader

class ProductViewModelFactory constructor(private val reader: BufferedReader) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            return ProductViewModel(reader) as T
        }
        throw IllegalArgumentException("UnknownViewModel")
    }
}