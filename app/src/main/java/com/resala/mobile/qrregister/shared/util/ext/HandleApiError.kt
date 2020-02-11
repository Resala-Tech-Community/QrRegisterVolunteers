/*
 * Created by  Mobile Dev Team  on 2/11/20 2:14 PM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.util.ext

import android.app.Activity
import android.content.Context
import com.google.gson.Gson
import com.resala.mobile.qrregister.R
import com.resala.mobile.qrregister.shared.data.model.NormalResponse
import com.resala.mobile.qrregister.shared.util.FlashbarUtil
import retrofit2.HttpException
import java.net.ConnectException
import java.util.concurrent.TimeoutException


fun Throwable.showError(context: Context): Boolean {
    when (this) {
        is HttpException -> {
            val gson = Gson()
            val errorResponse = gson.fromJson(
                this.response()?.errorBody()?.charStream(),
                NormalResponse::class.java
            )
            FlashbarUtil.show(errorResponse.message, activity = context as Activity)
        }
        is TimeoutException -> FlashbarUtil.show(
            context.getString(R.string.timeout),
            activity = context as Activity
        )
        is ConnectException -> FlashbarUtil.show(
            context.getString(R.string.device_is_offline),
            activity = context as Activity
        )
    }
    return false
}