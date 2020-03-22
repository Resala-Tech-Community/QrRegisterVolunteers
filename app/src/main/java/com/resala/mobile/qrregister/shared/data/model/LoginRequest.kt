/*
 * Created by  Mobile Dev Team  on 2/23/20 2:51 PM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.data.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String
)