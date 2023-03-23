package com.example.freshegokidproject

import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.widget.ImageView

class ProductLoadImageAsyncTask(val imageView: ImageView) : AsyncTask<Drawable, Unit, Unit>() {

    override fun doInBackground(vararg params: Drawable?): Unit {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        products
        assert(params != null) { "drawable was null" }
        imageView.setImageDrawable(params.get(0))
    }

}

