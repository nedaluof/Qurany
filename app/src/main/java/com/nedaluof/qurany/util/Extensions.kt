package com.nedaluof.qurany.util

import android.app.Activity
import android.widget.Toast
import androidx.annotation.StringRes

/**
 * Created by nedaluof on 12/13/2020.
 */

fun Activity.toast(@StringRes message: Int) =
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

fun Activity.toast(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
