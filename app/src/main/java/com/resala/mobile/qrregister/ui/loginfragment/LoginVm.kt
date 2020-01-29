/*
 * Created by  Mobile Dev Team  on 1/11/20 10:59 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.ui.loginfragment

import android.text.TextUtils
import android.view.View
import android.widget.EditText
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

    fun checkValidations(idEdt: EditText, passwordEdt: EditText) {

        var cancel = false
        var focusView: View? = null

        if (TextUtils.isEmpty(idEdt.text.toString())) {
            idEdt.error = "Required"
            focusView = idEdt
            cancel = true
        } else {
            idEdt.error = null
        }

        if (TextUtils.isEmpty(passwordEdt.text.toString())) {
            passwordEdt.error = "Required"
            focusView = passwordEdt
            cancel = true
        } else {
            passwordEdt.error = null
        }
        if (cancel) {
            focusView?.requestFocus()
        } else {
            //call api for checking credentials
            _checkValidations.value = Result(isSuccess = true)
        }
    }
    data class Result(val isSuccess: Boolean = false, val error: Throwable? = null)
}