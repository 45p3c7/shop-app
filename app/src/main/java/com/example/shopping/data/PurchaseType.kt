package com.example.shopping.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class PurchaseType : Parcelable {
    NEW, ARCHIVE
}