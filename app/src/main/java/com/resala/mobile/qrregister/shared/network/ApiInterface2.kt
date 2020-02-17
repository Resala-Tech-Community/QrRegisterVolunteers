/*
 * Created by  Mobile Dev Team  on 2/17/20 4:23 PM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.network


import com.resala.mobile.qrregister.shared.data.model.EventPOJO
import com.resala.mobile.qrregister.shared.data.model.NormalResponse
import io.reactivex.Observable
import retrofit2.http.*


interface ApiInterface2 {



    @FormUrlEncoded
    @POST("registration/volunteer")
    fun registerVolunteerByCode(
        @Field("branchId") branchId: String,
        @Field("code") code: String,
        @Field("eventId") eventId: String,
        @Field("phone") phone: String
    ): Observable<NormalResponse>


    @FormUrlEncoded
    @POST("registration/volunteer")
    fun registerVolunteerByData(
        @Field("EMail") EMail: String,
        @Field("branchId") branchId: String,
        @Field("eventId") eventId: String,
        @Field("gender") gender: String,
        @Field("name") name: String,
        @Field("phoneNumber") phoneNumber: String,
        @Field("regionId") regionId: String
    ): Observable<NormalResponse>

}