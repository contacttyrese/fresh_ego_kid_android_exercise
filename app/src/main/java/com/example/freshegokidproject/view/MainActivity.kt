package com.example.freshegokidproject.view

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.freshegokidproject.R
import com.example.freshegokidproject.databinding.ActivityMainBinding
import com.example.freshegokidproject.model.Product
import com.example.freshegokidproject.model.ProductDao
import com.example.freshegokidproject.viewmodel.ProductRecyclerViewAdapter
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val dao = ProductDao("first.json", this)
        val products = dao.mProducts.values.toList()
        val layoutManager = LinearLayoutManager(this)

        binding.homeProductList.setHasFixedSize(true)
        binding.homeProductList.setItemViewCacheSize(3)
        binding.homeProductList.isNestedScrollingEnabled = false
        binding.homeProductList.layoutManager = layoutManager
        binding.homeProductList.adapter = ProductRecyclerViewAdapter(this, products)
        binding.homeProductList.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))
        binding.homeProductList.findViewHolderForItemId(1)

        binding.searchButton.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            intent.putParcelableArrayListExtra("products", products as ArrayList<out Product>)
            startActivity(intent)
        }

        // Was using to retrieve images from URL
//        val discountBannerImage = findViewById<ImageView>(R.id.discount_banner_image)
//        DownloadImageTask(discountBannerImage).execute("https://cdn.shopify.com/s/files/1/2579/8156/files/MID-SEASON_SALE_FRESH_EGO_KID_3024x.png?v=1570440980")

    }

    override fun onDestroy() {
        super.onDestroy()
    }


}

// Use following function if loading image from URL
private class DownloadImageTask(var bmImage: ImageView) : AsyncTask<String, Void, Bitmap>() {

    override fun doInBackground(vararg urls: String): Bitmap {
        val urldisplay = urls[0]
        lateinit var mIcon: Bitmap

        try {
            val inputStream = java.net.URL(urldisplay).openStream()
            mIcon = BitmapFactory.decodeStream(inputStream)
        }
        catch (exception: Exception) {
            exception.printStackTrace()
        }
        return mIcon
    }

    override fun onPostExecute(result: Bitmap) {
        bmImage.setImageBitmap(result)
    }
}