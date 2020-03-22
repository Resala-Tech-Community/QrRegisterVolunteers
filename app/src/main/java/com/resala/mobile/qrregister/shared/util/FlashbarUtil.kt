/*
 * Created by  Mobile Dev Team  on 1/11/20 9:35 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.util

import android.app.Activity
import androidx.annotation.ColorRes
import com.andrognito.flashbar.Flashbar
import com.resala.mobile.qrregister.R


object FlashbarUtil {

    fun show(
        message: String,
        @ColorRes color: Int = R.color.colorPrimary,
        activity: Activity
    ) {
        try {
            Flashbar.Builder(activity)
                .message(message)
                .gravity(Flashbar.Gravity.TOP)
                .duration(6000)
                .messageAppearance(R.style.TextStyleFlashBar)
                .backgroundColorRes(color)
                .dismissOnTapOutside()
                .enableSwipeToDismiss()
                .build()
                .show()
        } catch (e: Exception) {
            e.printStackTrace()

        }

    }
}
