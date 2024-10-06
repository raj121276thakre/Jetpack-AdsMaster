package com.example.jetpackadsmaster.adsContainer.appOpenAds

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.appopen.AppOpenAd.AppOpenAdLoadCallback
import java.util.Date

interface OnShowAdCompleteListener {
    fun onShowAdComplete()
}

class AppOpenAdManager : Application.ActivityLifecycleCallbacks {
    private var currentActivity: Activity? = null
    private var adUnit: String

    private val lifecycleEventObserver = LifecycleEventObserver { source, event ->
        if (event == Lifecycle.Event.ON_RESUME) {
            currentActivity?.let {
                showAdIfAvailable(it)
            }
        } else if (event == Lifecycle.Event.ON_PAUSE) {

        }
    }


    constructor(application: Application, adUnitId: String) {
        this.adUnit = adUnitId
        application.registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleEventObserver)
    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {

    }

    override fun onActivityStarted(p0: Activity) {

    }

    override fun onActivityResumed(p0: Activity) {

    }

    override fun onActivityPaused(p0: Activity) {

    }

    override fun onActivityStopped(p0: Activity) {

    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {

    }

    override fun onActivityDestroyed(p0: Activity) {

    }

    private var appOpenAd: AppOpenAd? = null
    private var isLoadingAd: Boolean = false
    var isShowingAd: Boolean = false
    var loadTime: Long = 0

    fun loadAd(context: Context) {
        if (isLoadingAd || isAdAvailable()) {
            return
        }
        isLoadingAd = true
        var request = AdRequest.Builder().build()
        AppOpenAd.load(
            context,
            adUnit,
            request,
            object : AppOpenAdLoadCallback() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    isLoadingAd = false
                }

                override fun onAdLoaded(p0: AppOpenAd) {
                    super.onAdLoaded(p0)
                    appOpenAd = p0
                    isLoadingAd = false
                    loadTime = Date().time
                }

            }

        )
    }

    fun isAdAvailable(): Boolean {
        return appOpenAd != null
    }

    private fun showAdIfAvailable(it: Activity) {
        showAdIfAvailable(
            it,
            object : OnShowAdCompleteListener {
                override fun onShowAdComplete() {
                    TODO("Not yet implemented")
                }

            }
        )
    }

    private fun showAdIfAvailable(
        it: Activity,
        onShowAdCompleteListener: OnShowAdCompleteListener
    ) {
        if (isShowingAd) {
            return
        }

        if (!isAdAvailable()) {
            onShowAdCompleteListener.onShowAdComplete()
            loadAd(it)
            return
        }
        appOpenAd!!.setFullScreenContentCallback(
            object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    appOpenAd = null
                    isShowingAd = false
                    onShowAdCompleteListener.onShowAdComplete()
                    loadAd(it)
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    appOpenAd = null
                    isShowingAd = false
                    onShowAdCompleteListener.onShowAdComplete()
                    loadAd(it)
                }

                override fun onAdShowedFullScreenContent() {

                }

            }
        )
        isShowingAd = true
        appOpenAd!!.show(it)

    }


}

















































