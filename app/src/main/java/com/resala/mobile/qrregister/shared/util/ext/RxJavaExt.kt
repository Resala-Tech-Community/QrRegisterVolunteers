/*
 * Created by  Mobile Dev Team  on 1/11/20 9:35 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.util.ext

import com.resala.mobile.qrregister.shared.rx.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single


/**
 * Use SchedulerProvider configuration
 */
fun <T> Single<T>.with(schedulerProvider: SchedulerProvider): Single<T> = observeOn(schedulerProvider.ui()).subscribeOn(schedulerProvider.io())

fun Completable.with(schedulerProvider: SchedulerProvider): Completable = observeOn(schedulerProvider.ui()).subscribeOn(schedulerProvider.io())

fun <T> Observable<T>.with(schedulerProvider: SchedulerProvider): Observable<T> = observeOn(schedulerProvider.ui()).subscribeOn(schedulerProvider.io())

fun <T> Observable<T>.withDB(schedulerProvider: SchedulerProvider): Observable<T> = observeOn(schedulerProvider.io()).subscribeOn(schedulerProvider.computation())
