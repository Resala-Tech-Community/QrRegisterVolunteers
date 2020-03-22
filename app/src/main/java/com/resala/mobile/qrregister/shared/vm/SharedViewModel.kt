/*
 * Created by  Mobile Dev Team  on 2/23/20 1:55 PM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.resala.mobile.qrregister.shared.data.model.EventPOJO

class SharedViewModel : ViewModel() {
    val selected = MutableLiveData<EventPOJO>()

    fun select(item: EventPOJO) {
        selected.value = item
    }
}