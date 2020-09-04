package com.saitow.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter




/**
 * Created by Mostafa Anter on 9/4/20.
 */
class Helper {
    companion object{
        @JvmStatic
        @BindingAdapter("android:src")
        fun setImageViewResource(imageView: ImageView, resource: Int) {
            imageView.setImageResource(resource)
        }
    }
}