/*
 * Created by  Mobile Dev Team  on 1/11/20 10:43 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.ui.navhostactivity

import android.Manifest
import com.resala.mobile.qrregister.R
import com.resala.mobile.qrregister.shared.util.LanguagePrfs
import org.koin.android.viewmodel.ext.android.viewModel


class NavHostActivity : RunTimePermissionParentActivity() {

    override val vm: NavHostActivityVm by viewModel()
    override var layoutId: Int = R.layout.activity_nav_host

    private val permission = arrayOf(
        Manifest.permission.CAMERA
    )

    override fun doOnCreate() {
        super.doOnCreate()
        LanguagePrfs(activity(), false, vm.pref)
        setSupportActionBar(findViewById(R.id.toolbar))
        invalidateOptionsMenu()

        grantPermissions(permission)
    }


    override fun onPermissionGranted(granted: Boolean) {
        if (!granted) {
            grantPermissions(permission)
        }
    }


}



