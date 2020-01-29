/*
 * Created by  Mobile Dev Team  on 1/11/20 2:05 PM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.util

import com.resala.mobile.qrregister.BuildConfig


class BuildUtil {

    interface Type {
        companion object {
            val RELEASE = "release"
            val DEBUG = "debug"

        }
    }

    companion object {
        fun isDebug() = BuildConfig.BUILD_TYPE == Type.DEBUG
    }
}