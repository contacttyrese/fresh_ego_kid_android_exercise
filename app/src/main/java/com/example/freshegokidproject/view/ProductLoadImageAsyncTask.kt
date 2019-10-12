package com.example.freshegokidproject.view

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

//    override fun onPostExecute(result: Drawable?) {
//        super.onPostExecute(result)
//
//        imageView.setImageDrawable(drawable)
//    }

//    private fun getImageDrawable(productKey: String): Drawable? {
//        return when(productKey) {
//            "fek_460" -> ContextCompat.getDrawable(context, R.drawable.fek_460)
//            "fek_497" -> ContextCompat.getDrawable(context, R.drawable.fek_497)
//            "fek_500" -> ContextCompat.getDrawable(context, R.drawable.fek_500)
//            "fek_10193" -> ContextCompat.getDrawable(context, R.drawable.fek_10193)
//            "fek_10206" -> ContextCompat.getDrawable(context, R.drawable.fek_10206)
//            "fek_10225" -> ContextCompat.getDrawable(context, R.drawable.fek_10225)
//
//            else -> null
//        }
//    }

}

