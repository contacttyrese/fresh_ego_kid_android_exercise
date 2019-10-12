package com.example.freshegokidproject

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.freshegokidproject.model.ProductDao
import com.example.freshegokidproject.presenter.ProductRecyclerViewAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val productRecyclerView = findViewById<RecyclerView>(R.id.mainpage_product_list)
        val dao = ProductDao("first.json", this)
        val products = dao.mProducts.values.toList()
        val layoutManager = LinearLayoutManager(this)

        productRecyclerView.setHasFixedSize(true)
        productRecyclerView.setItemViewCacheSize(3)
        productRecyclerView.isNestedScrollingEnabled = false
        productRecyclerView.layoutManager = layoutManager
        productRecyclerView.adapter = ProductRecyclerViewAdapter(this, products)
        productRecyclerView.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))

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