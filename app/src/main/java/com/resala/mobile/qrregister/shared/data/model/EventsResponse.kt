/*
 * Created by  Mobile Dev Team  on 1/11/20 9:35 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class EventPOJO constructor(
    @PrimaryKey
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("branch")
    var branch: String,
    @SerializedName("date")
    var date: String

) {

    val titleForList: String
        get() = if (name.isNotEmpty()) name else branch

    val branchForList: String
        get() = if (branch.isNotEmpty()) branch else branch

    val dateForList: String
        get() = if (date.isNotEmpty()) date else branch

}


