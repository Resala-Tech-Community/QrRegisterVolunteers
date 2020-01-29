/*
 * Created by  Mobile Dev Team  on 1/11/20 9:35 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.rx;

import io.reactivex.Scheduler;


public interface SchedulerProvider {

    Scheduler ui();

    Scheduler computation();

    Scheduler io();

}
