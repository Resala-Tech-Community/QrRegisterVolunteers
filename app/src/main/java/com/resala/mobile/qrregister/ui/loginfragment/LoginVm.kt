/*
 * Created by  Mobile Dev Team  on 1/11/20 10:59 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.ui.loginfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.resala.mobile.qrregister.shared.data.DataManager
import com.resala.mobile.qrregister.shared.vm.BaseViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val loginModule = module {
    viewModel { LoginVm(get()) }
}

class LoginVm(dataManager: DataManager) : BaseViewModel(dataManager) {
    private var _checkValidations = MutableLiveData<Result>()
    val checkValidations: LiveData<Result> = _checkValidations


    data class Result(val isSuccess: Boolean = false, val error: Throwable? = null)
}