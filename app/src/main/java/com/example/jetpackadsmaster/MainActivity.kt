package com.example.jetpackadsmaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jetpackadsmaster.adsContainer.banner.AdmobBanner
import com.example.jetpackadsmaster.adsContainer.interstitialAds.interstitialAdsContainer
import com.example.jetpackadsmaster.adsContainer.rewardedAdsContainer.rewardedAds
import com.example.jetpackadsmaster.ui.theme.JetpackAdsMasterTheme
import com.farimarwat.composenativeadmob.nativead.BannerAdAdmobMedium
import com.farimarwat.composenativeadmob.nativead.BannerAdAdmobSmall
import com.farimarwat.composenativeadmob.nativead.rememberNativeAdState
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val backgroundScope = CoroutineScope(Dispatchers.IO)
        backgroundScope.launch {
            // Initialize the Google Mobile Ads SDK on a background thread.
            MobileAds.initialize(this@MainActivity) {}
        }

        setContent {
            val adstate = rememberNativeAdState(
                context = this@MainActivity,
                adUnitId = "ca-app-pub-3940256099942544/2247696110"
            )
            JetpackAdsMasterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    Column {
                        Spacer(modifier = Modifier.height(20.dp))

                        AdmobBanner(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxWidth()

                        )

                        Button(
                            onClick = { interstitialAdsContainer(activity = this@MainActivity) },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text(text = "Show Interstitial Ad")
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            onClick = { rewardedAds(activity = this@MainActivity) },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text(text = "Show Rewarded Ad")
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        //Small Banner
                        BannerAdAdmobSmall(loadedAd = adstate)
                        Spacer(modifier = Modifier.fillMaxWidth())
                        //Medium Banner
                        BannerAdAdmobMedium(loadedAd = adstate)


                    }


                }
            }
        }
    }


}



