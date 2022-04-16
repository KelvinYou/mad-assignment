package com.example.assignment.models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.scale
import com.google.firebase.firestore.Blob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.assignment.data.PushNotification
import java.io.ByteArrayOutputStream
import java.lang.Exception

// Crop and resize bitmap (upscale)
fun Bitmap.crop(width: Int, height: Int): Bitmap {
    // Source width, height and ratio
    val sw = this.width
    val sh = this.height
    val sratio = 1.0 * sw / sh

    // Target offset (x, y), width, height and ratio
    val x: Int
    val y: Int
    val w: Int
    val h: Int
    val ratio = 1.0 * width / height

    if (ratio >= sratio) {
        // Retain width, calculate height
        w = sw
        h = (sw / ratio).toInt()
        x = 0
        y = (sh - h) / 2
    }
    else {
        // Retain height, calculate width
        w = (sh * ratio).toInt()
        h = sh
        x = (sw - w) / 2
        y = 0
    }

    return Bitmap
        .createBitmap(this, x, y, w, h) // Crop
        .scale(width, height) // Resize
}

//fun sendNotification(notification: PushNotification, TAG : String) = CoroutineScope(Dispatchers.IO).launch {
//    try{
//        val response = RetrofitInstance.api.postNotification(notification)
//    }catch (e : Exception){
//        Log.e(TAG,e.toString())
//    }
//}

//Convert from Bitmap to Blob
@Suppress("DEPRECATION")
fun Bitmap.toBlob(): Blob {
    ByteArrayOutputStream().use {
        this.compress(Bitmap.CompressFormat.WEBP, 80, it)
        return Blob.fromBytes(it.toByteArray())
    }
}

// Convert from Blob to Bitmap
fun Blob.toBitmap(): Bitmap? {
    val bytes = this.toBytes()
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}

// Crop to Blob
fun ImageView.cropToBlob(width: Int, height: Int): Blob {
    if (this.drawable == null)
        return Blob.fromBytes(ByteArray(0))
    else
        return this.drawable.toBitmap().crop(width, height).toBlob()
}