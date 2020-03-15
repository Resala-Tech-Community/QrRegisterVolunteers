/*
 * Created by  Mobile Dev Team  on 3/15/20 11:42 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class RegisterResponse : Serializable {
    @SerializedName("id")
    var id: Int = 0

    @SerializedName("code")
    var code: String = ""

    @SerializedName("name")
    var name: String = ""

    @SerializedName("joinDate")
    var joinDate: String = ""

    @SerializedName("notes")
    var notes: String = ""

    @SerializedName("miniCamp")
    var miniCamp: Boolean = false

    @SerializedName("tshirt")
    var tshirt: Boolean = false

    @SerializedName("gender")
    var gender: String = ""

    @SerializedName("phoneNumber")
    var phoneNumber: String = ""

    @SerializedName("birthDate")
    var birthDate: String = ""

    @SerializedName("email")
    var email: String = ""
}