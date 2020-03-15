/*
 * Created by  Mobile Dev Team  on 1/11/20 10:59 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.ui.loginfragment


import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.resala.mobile.qrregister.shared.data.DataManager
import com.resala.mobile.qrregister.shared.util.ext.with
import com.resala.mobile.qrregister.shared.vm.BaseViewModel
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Response


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
    fun login(username: String, password: String) {
        _loginResponse.value = Result(isLoading = true)

        api.login(
            username, password
        )
            .with(scheduler)
            .subscribe({
                _loginResponse.value = Result(result = it)
            }, {
                _loginResponse.value = Result(error = it)
            })

    }

    data class Result(
        val result: Response<ResponseBody>? = null,
        val isLoading: Boolean = false,
        val error: Throwable? = null
    )


}