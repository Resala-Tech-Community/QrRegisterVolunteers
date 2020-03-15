/*
 * Created by  Mobile Dev Team
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.ui.eventsfragment

import Utils
import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.resala.mobile.qrregister.shared.data.DataManager
import com.resala.mobile.qrregister.shared.data.model.EventPOJO
import com.resala.mobile.qrregister.shared.util.ext.with
import com.resala.mobile.qrregister.shared.vm.BaseViewModel
import okhttp3.ResponseBody
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Response

val eventModule = module {
    viewModel { EventListVm(get()) }
}

class EventListVm(dataManager: DataManager) : BaseViewModel(dataManager) {

    private val _responseBody = MutableLiveData<ResponseBody>()
    val responseBody: LiveData<ResponseBody> = _responseBody

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    @SuppressLint("CheckResult")
    fun getEvents(session_id: String) {
        _responseBody.value = ResponseBody(isLoading = true)

        api.getEvents(session_id)
            .with(scheduler)
            .subscribe({ result ->
                _dataLoading.value = false
                _responseBody.value = ResponseBody(eventList = result)
            }, { throwable ->
                _dataLoading.value = false
                _responseBody.value = ResponseBody(errorMessage = throwable)
            }, {
                _responseBody.value = ResponseBody(isLoading = false)
            })
    }


    fun refresh() {
        if (Utils.isOnline(activity())) {
            getEvents(pref.session)
        } else {
            _dataLoading.value = false
            _responseBody.value = ResponseBody(isOffline = true)
        }

    }

    data class ResponseBody(
        val eventList: List<EventPOJO> = emptyList(),
        val isLoading: Boolean = false,
        val errorMessage: Throwable? = null,
        val isOffline: Boolean = false
    )

    private var _logoutResponse = MutableLiveData<Result>()
    val logoutResponse: LiveData<Result> = _logoutResponse

    data class Result(
        val result: Response<okhttp3.ResponseBody>? = null,
        val isLoading: Boolean = false,
        val error: Throwable? = null
    )


    @SuppressLint("CheckResult")
    fun logout(session_id: String) {
        _logoutResponse.value = Result(isLoading = true)
        api.logout(session_id)
            .with(scheduler)
            .subscribe({
                _logoutResponse.value = Result(result = it)
            }, {
                _logoutResponse.value = Result(error = it)
            })
    }
}