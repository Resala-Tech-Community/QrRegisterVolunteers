/*
 * Created by  Mobile Dev Team  on 1/11/20 9:35 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.data


import com.resala.mobile.qrregister.shared.databases.DBRepository
import com.resala.mobile.qrregister.shared.network.ApiRepository
import com.resala.mobile.qrregister.shared.rx.SchedulerProvider
import com.resala.mobile.qrregister.shared.util.SharedPref

open class DataManager(
        val pref: SharedPref,
        val api: ApiRepository,
        val database: DBRepository,
        val schedulerProvider: SchedulerProvider
)
