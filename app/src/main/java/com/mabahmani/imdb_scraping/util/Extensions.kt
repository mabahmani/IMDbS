package com.mabahmani.imdb_scraping.util

import android.content.Context
import android.content.res.Resources
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mabahmani.imdb_scraping.R

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

fun Context.showUnexpectedError(){
    Toast.makeText(this, resources.getString(R.string.unexpected_error_suggest), Toast.LENGTH_SHORT).show()
}