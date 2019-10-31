package com.example.freshegokidproject.view

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.freshegokidproject.R
import com.example.freshegokidproject.model.Product
import com.example.freshegokidproject.model.ProductDao
import com.example.freshegokidproject.viewmodel.ProductRecyclerOnItemTouchListener
import com.example.freshegokidproject.viewmodel.ProductRecyclerViewAdapter
import io.reactivex.rxjava3.core.Observable
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val productRecyclerView = findViewById<RecyclerView>(R.id.mainpage_product_list)
        val searchButton = findViewById<Button>(R.id.search_button)
        val dao = ProductDao("first.json", this)
        val products = dao.mProducts.values.toList()
        val layoutManager = LinearLayoutManager(this)
//        val observable = Observable.fromIterable<Product>(products.asIterable())
//        val observable = Observable.fromIterable(dao.mProducts.asIterable())

//        observable.subscribe {
//            it.value
//        }

//        observable.safeSubscribe(ProductObserver())

        productRecyclerView.setHasFixedSize(true)
        productRecyclerView.setItemViewCacheSize(3)
        productRecyclerView.isNestedScrollingEnabled = false
        productRecyclerView.layoutManager = layoutManager
        productRecyclerView.adapter = ProductRecyclerViewAdapter(this, products)
        productRecyclerView.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))
        productRecyclerView.findViewHolderForItemId(1)
//        productRecyclerView.item
//        productRecyclerView.addOnItemTouchListener(ProductRecyclerOnItemTouchListener())
//        productRecyclerView.addOnItemTouchListener()
//        RecyclerView.SimpleOnItemTouchListener() {
//
//        }

        searchButton.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            intent.putParcelableArrayListExtra("products", products as ArrayList<out Product>)
            startActivity(intent)
        }


//        ObservableOnSubscribe<> {  }

//        for (product in products) {
//            val observable: Observable<Product> = ObservableCreate<Product>(ObservableOnSubscribe<Product> {
//                    emitter -> emitter.onNext(product)
//                emitter.onComplete()
//            })
//        }
//        val observable: Observable<Product> = ObservableCreate<Product>(ObservableOnSubscribe<Product> {
//            emitter -> emitter.onNext(products.get(0))
//            emitter.onComplete()
//        })



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
            Log.e("Error", exception.message)
            exception.printStackTrace()
        }
        return mIcon
    }

    override fun onPostExecute(result: Bitmap) {
        bmImage.setImageBitmap(result)
    }
}