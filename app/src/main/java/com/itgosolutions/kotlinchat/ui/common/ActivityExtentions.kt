package com.itgosolutions.kotlinchat.ui.common

import android.app.Activity
import android.os.Build
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.view.WindowManager

fun Activity.setSystemBarColor(@ColorRes color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this, color)
    }
}