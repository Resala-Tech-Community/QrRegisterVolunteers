/*
 * Created by  Mobile Dev Team  on 2/11/20 2:14 PM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.util.ext

import android.app.Activity
import android.content.Context
import com.resala.mobile.qrregister.R
import com.resala.mobile.qrregister.shared.util.FlashbarUtil
import org.json.JSONObject
import retrofit2.HttpException
import java.net.ConnectException
import java.util.concurrent.TimeoutException


fun Throwable.showError(context: Context, isLogin: Boolean = false): Boolean {
    when (this) {
        is HttpException -> {
            checkError(this, context, isLogin)
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

private fun checkError(errorException: HttpException?, context: Context, isLogin: Boolean) {
    val errorBody = errorException?.response()?.errorBody()?.string()
    val mainObject = JSONObject(errorBody)
    FlashbarUtil.show(
        if (isLogin) mainObject.getString("message") else mainObject.getJSONObject("body").getString(
            "message"
        ),
        activity = context as Activity
    )

}