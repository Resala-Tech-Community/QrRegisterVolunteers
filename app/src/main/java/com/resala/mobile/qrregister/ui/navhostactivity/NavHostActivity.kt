/*
 * Created by  Mobile Dev Team  on 1/11/20 10:43 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.ui.navhostactivity

import com.resala.mobile.qrregister.R
import com.resala.mobile.qrregister.shared.ui.activity.BaseActivity
import org.koin.android.viewmodel.ext.android.viewModel


class NavHostActivity : BaseActivity<NavHostActivityVm>() {

    override val vm: NavHostActivityVm by viewModel()
    override var layoutId: Int = R.layout.activity_nav_host

    override fun doOnCreate() {
        super.doOnCreate()

    }


}


