package com.mammuten.spliteasy.util

import android.content.Context
import android.view.View
import android.widget.Toast

object MessageUtil {
    fun shortToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun longToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}