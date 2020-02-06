/*
 * Created by  Mobile Dev Team
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.ui.eventsfragment

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.resala.mobile.qrregister.shared.data.DataManager
import com.resala.mobile.qrregister.shared.data.model.EventPOJO
import com.resala.mobile.qrregister.shared.vm.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val eventModule = module {
    viewModel { EventListVm(get()) }
}

class EventListVm(dataManager: DataManager) : BaseViewModel(dataManager) {
    lateinit var eventListMutableLiveData: MutableLiveData<List<EventPOJO>>
    lateinit var load: MutableLiveData<Boolean>
    lateinit var errorMessage: MutableLiveData<Throwable>

    @SuppressLint("CheckResult")
    fun getEvent() {
        load.value = true

        api.getEvents()
            .subscribeOn(scheduler.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                eventListMutableLiveData.value = result
            }, { throwable ->
                errorMessage.value = throwable
            }, {
                load.value = false
            })
    }


}