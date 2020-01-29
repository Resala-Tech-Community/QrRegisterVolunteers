/*
 * Created by  Mobile Dev Team  on 1/11/20 9:35 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.datasource.util

import com.resala.mobile.qrregister.shared.rx.SchedulerProvider
import io.reactivex.schedulers.Schedulers

class TestSchedulerProvider : SchedulerProvider {
    override fun io() = Schedulers.trampoline()

    override fun ui() = Schedulers.trampoline()

    override fun computation() = Schedulers.trampoline()
}