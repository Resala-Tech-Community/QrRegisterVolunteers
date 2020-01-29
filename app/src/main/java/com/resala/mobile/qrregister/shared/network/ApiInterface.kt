/*
 * Created by  Mobile Dev Team  on 1/11/20 9:35 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.network


import com.resala.mobile.qrregister.shared.data.model.EventPOJO

import io.reactivex.Observable
import retrofit2.http.*


interface ApiInterface {


    @GET("vg2o6")
    fun getEvents(): Observable<List<EventPOJO>>



}