/*
 * Created by  Mobile Dev Team  on 1/11/20 9:35 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.network


import com.resala.mobile.qrregister.shared.data.model.EventPOJO
import com.resala.mobile.qrregister.shared.data.model.NormalResponse
import io.reactivex.Observable
import retrofit2.http.*


interface ApiInterface {

    @FormUrlEncoded
    @POST("")
    fun logIn(
        @Field("id") email: String,
        @Field("password") password: String
    ): Observable<NormalResponse>

    @FormUrlEncoded
    @POST("")
    fun logOut(
        @Field("id") email: String
    ): Observable<NormalResponse>

    @GET("event/all")
    fun getEvents(): Observable<List<EventPOJO>>

    @GET("event/{id}")
    fun getEventById(
        @Path("id") id: String
    ): Observable<EventPOJO>

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