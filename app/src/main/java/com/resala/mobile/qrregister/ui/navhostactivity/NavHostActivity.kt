/*
 * Created by  Mobile Dev Team  on 1/11/20 10:43 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.ui.navhostactivity

import android.Manifest
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.resala.mobile.qrregister.R
import org.koin.android.viewmodel.ext.android.viewModel


class NavHostActivity : RunTimePermissionParentActivity() {

    override val vm: NavHostActivityVm by viewModel()
    override var layoutId: Int = R.layout.activity_nav_host

    private lateinit var appBarConfiguration: AppBarConfiguration

    private val permission = arrayOf(
        Manifest.permission.CAMERA
    )

    override fun doOnCreate() {
        super.doOnCreate()
        setSupportActionBar(findViewById(R.id.toolbar))

        val navController: NavController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration =
            AppBarConfiguration.Builder(R.id.loginFrag)
                .build()
        setupActionBarWithNavController(navController, appBarConfiguration)
        grantPermissions(permission)
    }

    override fun onSupportNavigateUp(): Boolean {
//        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
        super.onBackPressed()
        return true
    }

    override fun onPermissionGranted(granted: Boolean) {
        if (!granted) {
            grantPermissions(permission)
        }
    }


}



