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
    lateinit var responseBody: MutableLiveData<ResponseBody>

    @SuppressLint("CheckResult")
    fun getEvents() {
        responseBody.value = ResponseBody(isLoading = true)

        api.getEvents()
            .subscribeOn(scheduler.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                responseBody.value = ResponseBody(eventList = result)
            }, { throwable ->
                responseBody.value = ResponseBody(errorMessage = throwable)
            }, {
                responseBody.value = ResponseBody(isLoading = false)
            })
    }

    data class ResponseBody(
        val eventList: List<EventPOJO> = emptyList(),
        val isLoading: Boolean = false,
        val errorMessage: Throwable? = null
    )

}