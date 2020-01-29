/*
 * Created by  Mobile Dev Team  on 1/11/20 9:35 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.koin

import android.content.Context

import com.resala.mobile.qrregister.ui.loginfragment.loginModule
import com.resala.mobile.qrregister.ui.navhostactivity.navHostActivityModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KoinHelper {
    companion object {
        fun start(context: Context) {
            startKoin {
                androidContext(context)
                modules(
                    listOf(
                        appModule,
                        navHostActivityModule,
                        loginModule



                    )
                )
            }
        }
    }
}