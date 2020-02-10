/*
 * Created by  Mobile Dev Team  on 1/11/20 10:49 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.ui.loginfragment

import com.resala.mobile.qrregister.R
import com.resala.mobile.qrregister.shared.ui.frag.BaseFrag
import org.koin.android.viewmodel.ext.android.viewModel


class LoginFrag : BaseFrag<LoginVm>() {


    override val vm: LoginVm by viewModel()
    override var layoutId: Int = R.layout.login_frag


    override fun doOnViewCreated() {
        super.doOnViewCreated()

    }


}
