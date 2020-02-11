/*
 * Created by  Mobile Dev Team  on 1/11/20 10:49 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.ui.loginfragment

import android.view.View
import android.widget.EditText
import com.resala.mobile.qrregister.R
import com.resala.mobile.qrregister.shared.ui.frag.BaseFrag
import com.resala.mobile.qrregister.shared.util.isNullOrEmpty
import org.koin.android.viewmodel.ext.android.viewModel


class LoginFrag : BaseFrag<LoginVm>() {
    override val vm: LoginVm by viewModel()
    override var layoutId: Int = R.layout.frag_login


    override fun doOnViewCreated() {
        super.doOnViewCreated()

    }


    fun checkValidations(idEdt: EditText, passwordEdt: EditText) {
        var focusView: View? = null

        if (!isNullOrEmpty(idEdt.text.toString())) {
            //Take Action
            idEdt.error = "Required"
            focusView = idEdt
        } else if (!isNullOrEmpty(passwordEdt.text.toString())) {
            //Take Action
            passwordEdt.error = "Required"
            focusView = passwordEdt
        } else {
            //request Login API
            focusView?.requestFocus()

        }

    }

}
