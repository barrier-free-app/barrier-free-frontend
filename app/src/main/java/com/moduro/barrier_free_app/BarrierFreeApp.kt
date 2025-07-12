package com.moduro.barrier_free_app

import android.app.Application
import android.util.Log
import com.moduro.barrier_free_app.BuildConfig.NAVER_CLIENT_ID
import com.naver.maps.map.NaverMapSdk
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BarrierFreeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initNaverMapSdk()
        setTimber()
    }

    private fun initNaverMapSdk() {
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NcpKeyClient(NAVER_CLIENT_ID)
        Log.d("NaverClientID", NAVER_CLIENT_ID)
    }

    private fun setTimber() {
        Timber.plant(Timber.DebugTree())
    }
}