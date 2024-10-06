package com.example.jetpackadsmaster

import androidx.multidex.MultiDexApplication
import com.example.jetpackadsmaster.adsContainer.appOpenAds.AppOpenAdManager
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class Application : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        val backgroundScope = CoroutineScope(Dispatchers.IO)
        // backgroundScope.launch {
        // Initialize the Google Mobile Ads SDK on a background thread.
        MobileAds.initialize(this@Application) {}

        // }
        AppOpenAdManager(
            application = this,
            adUnitId = "ca-app-pub-3940256099942544/9257395921"
        )
    }

}