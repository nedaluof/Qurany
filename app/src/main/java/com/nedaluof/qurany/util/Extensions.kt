package com.nedaluof.qurany.util

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import com.nedaluof.qurany.R
import com.tapadoo.alerter.Alerter
import es.dmoral.toasty.Toasty

/**
 * Created by nedaluof on 12/13/2020.
 */

fun Activity.toast(@StringRes message: Int) =
  Toast.makeText(this, message, Toast.LENGTH_LONG).show()

fun Activity.toast(message: String) =
  Toast.makeText(this, message, Toast.LENGTH_LONG).show()

fun Activity.alertNoInternet() =
  Alerter.create(this)
    .setTitle(R.string.alrt_no_internet_title)
    .setText(R.string.alrt_no_internet_msg)
    .enableSwipeToDismiss()
    .hideIcon()
    .setBackgroundColorRes(R.color.red)
    .show()

fun Context.toastyError(@StringRes msg: Int) =
        Toasty.error(this, msg).show()

fun Context.toastyError(msg: String) =
        Toasty.error(this, msg).show()

fun Context.toastySuccess(@StringRes msg: Int) =
        Toasty.success(this, msg).show()

fun Context.toastySuccess(msg: String) =
        Toasty.success(this, msg).show()

fun Context.toastyInfo(@StringRes msg: Int) =
        Toasty.info(this, msg).show()

fun Context.toastyInfo(msg: String) =
        Toasty.info(this, msg).show()
