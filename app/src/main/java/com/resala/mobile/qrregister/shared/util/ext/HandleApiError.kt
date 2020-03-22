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


fun Throwable.showError(context: Context): Boolean {
    when (this) {
        is HttpException -> {
            checkError(this, context)
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

private fun checkError(errorException: HttpException?, context: Context) {
    val errorBody = errorException?.response()?.errorBody()?.string()
    val mainObject = JSONObject(errorBody)
    FlashbarUtil.show(
        mainObject.getString("message"),
        activity = context as Activity
    )

}