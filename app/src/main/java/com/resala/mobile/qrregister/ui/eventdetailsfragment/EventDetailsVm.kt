/*
 * Created by  Mobile Dev Team  on 2/17/20 12:06 PM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.ui.eventdetailsfragment

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.resala.mobile.qrregister.shared.data.DataManager
import com.resala.mobile.qrregister.shared.data.model.EventPOJO
import com.resala.mobile.qrregister.shared.data.model.Region
import com.resala.mobile.qrregister.shared.data.model.RegisterResponse
import com.resala.mobile.qrregister.shared.util.ext.with
import com.resala.mobile.qrregister.shared.vm.BaseViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val eventDetailsVmModule = module {
    viewModel { EventDetailsVm(get()) }
}

class EventDetailsVm(dataManager: DataManager) : BaseViewModel(dataManager) {

    private val _toggleSheet = MutableLiveData<Unit>()
    val toggleSheet: LiveData<Unit> = _toggleSheet

    private val _isSheetHidden = MutableLiveData<Boolean>()
    val isSheetHidden: LiveData<Boolean> = _isSheetHidden

    private val _ishShowDots = MutableLiveData<Boolean>()
    val ishShowDots: LiveData<Boolean> = _ishShowDots


    private val _toggleReveelView = MutableLiveData<Boolean>()
    val toggleReveelView: LiveData<Boolean> = _toggleReveelView


    private val _registerData = MutableLiveData<Unit>()
    val registerData: LiveData<Unit> = _registerData


    init {
        _isSheetHidden.value = true
        _toggleReveelView.value = false
        _ishShowDots.value = false
    }

    fun openSheet() {
        _toggleSheet.value = Unit
    }

    fun registerData() {
        _registerData.value = Unit
    }

    fun showHideText(isSheetHidden: Boolean) {
        _isSheetHidden.value = isSheetHidden
    }

    fun showHideDots(ishShowDots: Boolean) {
        _ishShowDots.value = ishShowDots

    }

    fun toggleRevealView(isShow: Boolean) {
        _toggleReveelView.value = isShow

    }

    /**
     * get event details api
     * */
    private val _responseBody = MutableLiveData<ResponseBody>()
    val responseBody: LiveData<ResponseBody> = _responseBody

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    @SuppressLint("CheckResult")
    fun getEventById(id: String) {
        _responseBody.value = ResponseBody(isLoading = true)

        api.getEventById(id)
            .with(scheduler)
            .subscribe({ result ->
                _dataLoading.value = false
                _responseBody.value = ResponseBody(event = result)
            }, { throwable ->
                _dataLoading.value = false
                _responseBody.value = ResponseBody(errorMessage = throwable)
            }, {
                _responseBody.value = ResponseBody(isLoading = false)
            })
    }


    data class ResponseBody(
        val event: EventPOJO? = null,
        val isLoading: Boolean = false,
        val errorMessage: Throwable? = null,
        val isOffline: Boolean = false
    )

    /**
     *      register volunteer data from scanned QR Code or using Id/Number
     * */
    private val _responseRegisterBody = MutableLiveData<ResponseRegisterBody>()
    val responseRegisterBody: LiveData<ResponseRegisterBody> = _responseRegisterBody


    @SuppressLint("CheckResult")
    fun registerVolunteerByCodeOrNumber(
        session_id: String,
        branchId: String,
        code: String?,
        eventId: String,
        phone: String?
    ) {
        _responseRegisterBody.value = ResponseRegisterBody(isLoading = true)

        api.registerVolunteerByCode(
            session_id,
            branchId,
            code,
            eventId,
            phone
        )
            .with(scheduler)
            .subscribe({ result ->
                _dataLoading.value = false
                _responseRegisterBody.value = ResponseRegisterBody(result = result)
            }, { throwable ->
                _dataLoading.value = false
                _responseRegisterBody.value = ResponseRegisterBody(errorMessage = throwable)
            }, {
                _responseRegisterBody.value = ResponseRegisterBody(isLoading = false)
            })
    }

    /**
     *      register new volunteer by data  (email,gender,name, etc...)
     * */
    @SuppressLint("CheckResult")
    fun registerVolunteerByData(
        session_id: String,
        EMail: String,
        branchId: String,
        eventId: String,
        gender: String,
        name: String,
        phoneNumber: String,
        regionId: String
    ) {
        _responseRegisterBody.value = ResponseRegisterBody(isLoading = true)

        api.registerVolunteerByData(
            session_id,
            EMail,
            branchId,
            eventId,
            gender,
            name,
            phoneNumber,
            regionId
        )
            .with(scheduler)
            .subscribe({ result ->
                _dataLoading.value = false
                _responseRegisterBody.value = ResponseRegisterBody(result = result)
            }, { throwable ->
                _dataLoading.value = false
                _responseRegisterBody.value = ResponseRegisterBody(errorMessage = throwable)
            }, {
                _responseRegisterBody.value = ResponseRegisterBody(isLoading = false)
            })
    }


    data class ResponseRegisterBody(
        val result: RegisterResponse? = null,
        val isLoading: Boolean = false,
        val errorMessage: Throwable? = null,
        val isOffline: Boolean = false
    )


    /**
     *      get regions of resala branches
     * */
    private val _responseRegionsBody = MutableLiveData<ResponseRegionBody>()
    val responseRegionsBody: LiveData<ResponseRegionBody> = _responseRegionsBody


    @SuppressLint("CheckResult")
    fun getRegions(
        session_id: String
    ) {
        _responseRegionsBody.value = ResponseRegionBody(isLoading = true)

        api.getRegions(
            session_id

        )
            .with(scheduler)
            .subscribe({ result ->
                _dataLoading.value = false
                _responseRegionsBody.value = ResponseRegionBody(result = result)
            }, { throwable ->
                _dataLoading.value = false
                _responseRegionsBody.value = ResponseRegionBody(errorMessage = throwable)
            }, {
                _responseRegionsBody.value = ResponseRegionBody(isLoading = false)
            })
    }


    data class ResponseRegionBody(
        val result: ArrayList<Region>? = null,
        val isLoading: Boolean = false,
        val errorMessage: Throwable? = null,
        val isOffline: Boolean = false
    )


}