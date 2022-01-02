package com.mabahmani.imdb_scraping.util

import android.content.Context
import android.content.res.Resources
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mabahmani.imdb_scraping.R

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