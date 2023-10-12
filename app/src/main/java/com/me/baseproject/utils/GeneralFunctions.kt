package com.me.baseproject.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.me.baseproject.R

class GeneralFunctions {
    fun glide(activity: Context, urlString: String, imageView: ImageView) {
        val url = Uri.parse(urlString)
        Glide.with(activity)
            .load(url)
            .centerCrop()
            .into(imageView)
            .apply {
                RequestOptions.placeholderOf(R.drawable.img_placeholder)
            }
    }

    fun glideFull(activity: Context, urlString: String, imageView: ImageView) {
        val url = Uri.parse(urlString)
        Glide.with(activity)
            .load(url)
            .into(imageView)
            .apply {
                RequestOptions.placeholderOf(R.drawable.img_placeholder)
            }
    }

    fun showToast(activity: Context, message: String, toastLength: Int = Toast.LENGTH_LONG){
        Toast.makeText(activity, message, toastLength).show()
    }
}