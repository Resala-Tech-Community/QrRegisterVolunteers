/*
 * Created by  Mobile Dev Team  on 1/11/20 9:35 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.datasource

import com.resala.mobile.qrregister.datasource.di.testApp
import org.junit.Test
import org.koin.core.context.startKoin

class DryRunTest : BaseUnitTest() {

    @Test
    fun testConfiguration() {
        startKoin { modules(testApp) }
    }

}