/*
 * Created by  Mobile Dev Team  on 1/11/20 9:35 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/* "id": 4,
        "eventDate": "05/02/2020",
        "name": "test event 4",
        "startTime": "09:00",
        "lastTimeToReg": "10:00"*/

@Entity
data class EventPOJO constructor(
    @PrimaryKey
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("eventDate")
    var date: String,
    @SerializedName("startTime")
    var startTime: String,
    @SerializedName("lastTimeToReg")
    var lastTimeToReg: String

) {

//    val time: String
//        get() = if (date.isNotEmpty()) "11:30" else startTime


}


