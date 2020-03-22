/*
 * Created by  Mobile Dev Team  on 1/11/20 9:35 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.resala.mobile.qrregister.shared.util.ext.toDate
import com.resala.mobile.qrregister.shared.util.ext.toDateTime

/*    {
        "branchId": 1,
        "eventId": 2,
        "eventDate": "1582397342000",
        "name": "test event 2",
        "startTime": "09:00",
        "lastTimeToReg": "10:00"
    },*/

@Entity
data class EventPOJO constructor(
    @PrimaryKey
    @SerializedName("eventId")
    var eventId: Int,
    @SerializedName("branchId")
    var branchId: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("eventDate")
    var date: String,
    @SerializedName("startTime")
    var startTime: String,
    @SerializedName("lastTimeToReg")
    var lastTimeToReg: String

) {

    val formattedDate: String
        get() = if (date.isNotEmpty()) date.toDateTime() else "dd/mm/yyyy"

    val dateInList: String
        get() = if (date.isNotEmpty()) date.toDate() else "dd/mm/yyyy"


}


