package com.example.freshegokidproject

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.freshegokidproject.model.ProductsDao
import com.example.freshegokidproject.presenter.ProductRecyclerViewAdapter
import java.io.FileReader

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val productRecyclerView = findViewById<RecyclerView>(R.id.mainpage_product_list)
        val dao = ProductsDao("first.json", this)

        productRecyclerView.layoutManager = LinearLayoutManager(this)
        productRecyclerView.adapter = ProductRecyclerViewAdapter(this, dao.mProducts)

        // Used to start new activity
//        val intent = Intent(this, MainPageProductListActivity::class.java)
//        startActivity(intent)

        // Was using to retrieve images from URL
//        val discountBannerImage = findViewById<ImageView>(R.id.discount_banner_image)
//        DownloadImageTask(discountBannerImage).execute("https://cdn.shopify.com/s/files/1/2579/8156/files/MID-SEASON_SALE_FRESH_EGO_KID_3024x.png?v=1570440980")

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
            Log.e("Error", exception.message)
            exception.printStackTrace()
        }
        return mIcon
    }

    override fun onPostExecute(result: Bitmap) {
        bmImage.setImageBitmap(result)
    }
}