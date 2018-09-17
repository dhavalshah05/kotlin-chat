package com.itgosolutions.kotlinchat.ui.common

import android.app.Activity
import android.content.pm.PackageManager
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

fun Activity.hasPermission(permissionString: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permissionString) == PackageManager.PERMISSION_GRANTED
}

fun Activity.netPermissions(permissions: Array<String>): Array<String> {
    val netPermissions = mutableListOf<String>()
    for (permission in permissions) {
        if (!hasPermission(permission))
            netPermissions.add(permission)
    }
    return netPermissions.toTypedArray()
}


