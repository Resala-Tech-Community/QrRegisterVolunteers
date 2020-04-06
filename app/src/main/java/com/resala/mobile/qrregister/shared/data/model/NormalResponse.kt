/*
 * Created by  Mobile Dev Team  on 1/11/20 9:35 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class NormalResponse : Serializable {

    @SerializedName("timestamp")
    var timestamp: String = ""

    @SerializedName("status")
    var status: Int = 0

    @SerializedName("error")
    var error: String = ""

    @SerializedName("message")
    var message: String = ""

    @SerializedName("path")
    var path: String = ""

}