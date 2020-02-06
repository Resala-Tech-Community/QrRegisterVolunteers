/*
 * Created by  Mobile Dev Team  on 1/11/20 10:43 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.ui.navhostactivity

import android.content.Intent
import com.resala.mobile.qrregister.R
import com.resala.mobile.qrregister.shared.ui.activity.BaseActivity
import com.resala.mobile.qrregister.ui.barCode.BarCodeActivity
import com.resala.mobile.qrregister.ui.barCode.utils.BarCodePermissions
import org.koin.android.viewmodel.ext.android.viewModel


class NavHostActivity : BaseActivity<NavHostActivityVm>() {

    override val vm: NavHostActivityVm by viewModel()
    override var layoutId: Int = R.layout.activity_nav_host
    private lateinit var barCodePermissions: BarCodePermissions


    override fun doOnCreate() {
        super.doOnCreate()
        barCodePermissions = BarCodePermissions(this) {
            this.startActivity(Intent(this, BarCodeActivity::class.java))
        }
        barCodePermissions.checkPermission()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        this.barCodePermissions.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
