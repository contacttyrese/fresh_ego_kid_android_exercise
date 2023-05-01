package com.example.freshegokidproject.model

import android.os.Parcel
import android.os.Parcelable

data class Product(val key: String,
                   val title: String,
                   val price: String,
                   val description: String,
                   val imageUrl: String = "default") : Parcelable  {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        "default"
    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(key)
        parcel.writeString(title)
        parcel.writeString(price)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }

}