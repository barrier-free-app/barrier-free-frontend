package com.moduro.barrier_free_app.presentation.auth.screen

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

fun openCustomTab(context: Context, url: String) {
    val intent = CustomTabsIntent.Builder()
        .setShowTitle(true)
        .build()
    intent.launchUrl(context, Uri.parse(url))
}