package com.filimo.demo.data.prefs

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UrlPreferenceManager @Inject constructor(@ApplicationContext context: Context) {

    private val prefs = context.getSharedPreferences("url_prefs", Context.MODE_PRIVATE)

    fun saveNextUrl(url: String?) {
        prefs.edit().putString(KEY_NEXT_URL, url).apply()
    }

    fun getNextUrl(): String? {
        return prefs.getString(KEY_NEXT_URL, null)
    }

    companion object {
        private const val KEY_NEXT_URL = "next_url"
    }
}