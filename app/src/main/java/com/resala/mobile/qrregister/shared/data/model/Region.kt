/*
 * Created by  Mobile Dev Team  on 3/20/20 9:12 PM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class Region : Serializable {
    @SerializedName("id")
    var id: Int = 0

    @SerializedName("name")
    var name: String = ""

}