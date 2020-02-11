/*
 * Created by  Mobile Dev Team  on 1/11/20 10:59 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.ui.loginfragment

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.resala.mobile.qrregister.shared.data.DataManager
import com.resala.mobile.qrregister.shared.data.model.NormalResponse
import com.resala.mobile.qrregister.shared.util.ext.with
import com.resala.mobile.qrregister.shared.vm.BaseViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val loginModule = module {
    viewModel { LoginVm(get()) }
}

class LoginVm(dataManager: DataManager) : BaseViewModel(dataManager) {

    private val _login = MutableLiveData<Unit>()
    val login: LiveData<Unit> = _login


    private var _loginResponse = MutableLiveData<Result>()
    val loginResponse: LiveData<Result> = _loginResponse

    fun doLogin() {
        _login.value = Unit
    }

    @SuppressLint("CheckResult")
    fun login(id: String, password: String) {
        _loginResponse.value = Result(isLoading = true)
        api.login(id, password)
            .with(scheduler)
            .subscribe({
                _loginResponse.value = Result(data = it)
            }, {
                _loginResponse.value = Result(error = it)
            })

    }

    data class Result(
        val data: NormalResponse? = null,
        val isLoading: Boolean = false,
        val error: Throwable? = null
    )

}