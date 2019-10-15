//package com.example.freshegokidproject.model
//
//import android.os.Parcel
//import android.os.Parcelable
//
//class ProductParcelable(override val key: String, override val description: String, override val price: String) : Parcelable, ProductBase {
//    constructor(parcel: Parcel) : this(
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString()
//    ) {
//    }
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(key)
//        parcel.writeString(description)
//        parcel.writeString(price)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<ProductParcelable> {
//        override fun createFromParcel(parcel: Parcel): ProductParcelable {
//            return ProductParcelable(parcel)
//        }
//
//        override fun newArray(size: Int): Array<ProductParcelable?> {
//            return arrayOfNulls(size)
//        }
//    }
//
//}