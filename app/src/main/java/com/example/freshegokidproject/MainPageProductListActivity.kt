package com.example.freshegokidproject

import android.app.Application
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.freshegokidproject.model.ProductsDao
import com.example.freshegokidproject.presenter.ProductRecyclerViewAdapter
import org.json.JSONObject
import java.io.*
import java.net.URL

class MainPageProductListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Activity should speak with dao or presenter to understand how many products there are

    }

}