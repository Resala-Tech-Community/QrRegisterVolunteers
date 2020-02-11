/*
 * Created by  Mobile Dev Team  on 2/10/20 4:34 PM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.ui.navhostactivity

import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.resala.mobile.qrregister.shared.ui.activity.BaseActivity

abstract class RunTimePermissionParentActivity : BaseActivity<NavHostActivityVm>() {

    abstract fun onPermissionGranted(granted: Boolean)

    protected  fun grantPermissions(permissions: Array<String>) {
        if (Build.VERSION.SDK_INT >= 23) {
            var permissionAdded = true
            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    permissionAdded = false
                }
            }
            if (!permissionAdded) {
                ActivityCompat.requestPermissions(this, permissions, 1)
            } else {
                onPermissionGranted(true)
            }
        } else {
            onPermissionGranted(true)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0])
            onPermissionGranted(true)
        } else {
            onPermissionGranted(false)

        }
    }

    companion object {
        private val TAG = NavHostActivity::class.java.name
    }


}