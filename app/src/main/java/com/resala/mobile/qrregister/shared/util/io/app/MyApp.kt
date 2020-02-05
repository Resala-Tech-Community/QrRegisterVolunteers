/*
 * Created by  Mobile Dev Team  on 1/11/20 9:35 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.util.io.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.FirebaseApp
import com.resala.mobile.qrregister.shared.koin.KoinHelper


class MyApp : Application() {


    companion object {

        fun applicationContext(): Context {
            return context.applicationContext
        }

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        @JvmStatic
        fun string(@StringRes res: Int): String {
            return context.getString(res)
        }

    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        //updateAndroidSecurityProvider()
        try {
            KoinHelper.start(this)
            context = applicationContext

        } catch (e: Exception) {

        }
    }

//    //solve TLS problem android <= 4.0
//    private fun updateAndroidSecurityProvider() {
//        try {
//            ProviderInstaller.installIfNeeded(this)
//        } catch (e: GooglePlayServicesRepairableException) {
//            Log.e("NearBy", "PlayServices not installed")
//        } catch (e: GooglePlayServicesNotAvailableException) {
//            Log.e("NearBy", "Google Play Services not available.")
//        }
//    }

}
