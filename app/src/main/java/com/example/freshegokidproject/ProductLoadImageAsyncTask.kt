package com.example.freshegokidproject

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.freshegokidproject.R
import com.example.freshegokidproject.model.Product

class ProductLoadImageAsyncTask(val imageView: ImageView) : AsyncTask<Drawable, Unit, Unit>() {

    override fun doInBackground(vararg params: Drawable?): Unit {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        products
        assert(params != null) { "drawable was null" }
        imageView.setImageDrawable(params.get(0))
    }

}

