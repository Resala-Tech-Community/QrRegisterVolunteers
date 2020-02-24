/*
 * Created by  Mobile Dev Team  on 2/23/20 2:15 PM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.util.ext

import java.text.SimpleDateFormat
import java.util.*

fun String.toDateTime(): String {
    try {
        val sdf = SimpleDateFormat("EEE, d MMM yyyy")
        val netDate = Date(this.toLong())
        return sdf.format(netDate)
    } catch (e: Exception) {
        return e.toString()
    }
}

fun String.toDate(): String {
    try {
        val sdf = SimpleDateFormat("dd MMM yyyy")
        val netDate = Date(this.toLong())
        return sdf.format(netDate)
    } catch (e: Exception) {
        return e.toString()
    }
}