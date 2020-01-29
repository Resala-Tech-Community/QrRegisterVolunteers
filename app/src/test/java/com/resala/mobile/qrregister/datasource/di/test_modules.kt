/*
 * Created by  Mobile Dev Team  on 1/11/20 9:35 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.datasource.di


import com.resala.mobile.qrregister.datasource.util.TestSchedulerProvider
import com.resala.mobile.qrregister.shared.koin.appModule

import org.koin.dsl.module


val testRxModule = module { single { TestSchedulerProvider() } }

val testApp = appModule