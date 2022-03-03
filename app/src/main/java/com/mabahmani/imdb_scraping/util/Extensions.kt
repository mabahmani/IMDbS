package com.mabahmani.imdb_scraping.util

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mabahmani.imdb_scraping.BuildConfig
import com.mabahmani.imdb_scraping.R
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

fun String?.orDefault() = this ?: ""

fun Int?.orZero() = this ?: 0

fun Long?.orDefault() = this ?: 0L

fun Float?.orZero() = this ?: 0f

fun Boolean?.orTrue() = this ?: true

fun Boolean?.orFalse() = this ?: false

fun Int.dp() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Float.dp() = (this * Resources.getSystem().displayMetrics.density)

fun Context.showNetworkConnectionError(retry: () -> Unit){
    MaterialAlertDialogBuilder(this)
        .setTitle(resources.getString(R.string.network_connection_error))
        .setMessage(resources.getString(R.string.network_connection_error_suggest))
        .setPositiveButton(resources.getString(R.string.try_again)) { _, _ ->
            retry.invoke()
        }
        .setCancelable(false)
        .show()
}

fun Context.showUnexpectedError(message: String) {
    if (BuildConfig.DEBUG){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    else{
        Toast.makeText(this, resources.getString(R.string.unexpected_error_suggest), Toast.LENGTH_SHORT).show()
    }
}

fun Int.formatNumber(): String{
    return try {
        NumberFormat.getInstance(Locale.US).format(this)
    } catch (e: Exception) {
        ""
    }
}

fun String.formatNumber(): String{
    return try {
        NumberFormat.getInstance(Locale.US).format(this.toInt())
    } catch (e: Exception) {
        ""
    }
}

fun String.formatTwoDecimalNumber(): String {
    return try {
        DecimalFormat("0.00").format(this.toDouble())
    } catch (e: Exception) {
        ""
    }
}

fun View.showKeyboard() {
    this.requestFocus()
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.hideKeyboard() {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun Context.toast(msg: String){
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}