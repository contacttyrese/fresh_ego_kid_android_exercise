package com.example.freshegokidproject.viewmodel

import com.example.freshegokidproject.R

enum class ImageResourceEnum {
    FEK_460 {
        override fun getResourceId() = R.drawable.fek_460
    },
    FEK_497 {
        override fun getResourceId() = R.drawable.fek_497
    },
    FEK_500 {
        override fun getResourceId() = R.drawable.fek_500
    },
    FEK_10193 {
        override fun getResourceId() = R.drawable.fek_10193
    },
    FEK_10206 {
        override fun getResourceId() = R.drawable.fek_10206
    },
    FEK_10225 {
        override fun getResourceId() = R.drawable.fek_10225
    };

    abstract fun getResourceId() : Int
}