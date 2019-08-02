package com.example.shopping.extension

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream


fun Bitmap.toByte() : ByteArray {
    val stream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, stream)
    this.recycle()
    return stream.toByteArray()
}

