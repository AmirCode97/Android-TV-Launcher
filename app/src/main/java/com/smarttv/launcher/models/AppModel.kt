package com.smarttv.launcher.models

import android.graphics.drawable.Drawable

data class AppModel(
    val packageName: String,
    val appName: String,
    val icon: Drawable? = null,
    val iconResId: Int? = null,
    val isSystemApp: Boolean = false,
    val category: String = "",
    val description: String = "",
    val rating: Float = 0f,
    val downloads: Long = 0
)
