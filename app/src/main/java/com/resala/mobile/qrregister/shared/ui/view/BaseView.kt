/*
 * Created by  Mobile Dev Team  on 1/11/20 9:35 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.ui.view


import android.content.Context
import com.abualgait.msayed.nursury.shared.ui.frag.BaseDialogFrag
import com.resala.mobile.qrregister.shared.ui.frag.BaseFrag
import com.resala.mobile.qrregister.shared.ui.activity.BaseActivity
import com.resala.mobile.qrregister.shared.vm.BaseViewModel

interface BaseView {


    fun baseViewModel(): BaseViewModel?

    fun activity(): BaseActivity<*>?

    fun context(): Context? {
        return activity()
    }

    fun showLoading() {}

    fun hideLoading() {}

    fun fragment(): BaseFrag<*> {
        throw UnsupportedOperationException()
    }

    fun dialogFragment(): BaseDialogFrag<*> {
        throw UnsupportedOperationException()
    }

}
