/*
 * Created by  Mobile Dev Team  on 1/11/20 9:35 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.network


import com.resala.mobile.qrregister.shared.data.model.EventPOJO
import com.resala.mobile.qrregister.shared.data.model.RegisterResponse
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

@JvmSuppressWildcards
interface ApiInterface {


    @GET("success")
    fun logIn(@Header("Authorization") auth: String): Observable<Response<ResponseBody>>

    @GET("logout")
    fun logOut(
        @Header("cookie") sessionId: String
    ): Observable<Response<ResponseBody>>

    @GET("event")
    fun getEvents(
        @Header("cookie") sessionId: String
    ): Observable<List<EventPOJO>>

    @GET("event/{id}")
    fun getEventById(
        @Path("id") id: String
    ): Observable<EventPOJO>

    @FormUrlEncoded
    @POST("registration/volunteer")
    fun registerVolunteerByCode(
        @Header("cookie") sessionId: String,
        @Field("branchId") branchId: String,
        @Field("code") code: String,
        @Field("eventId") eventId: String,
        @Field("phone") phone: String
    ): Observable<RegisterResponse>


    @FormUrlEncoded
    @POST("registration")
    fun registerVolunteerByData(
        @Header("cookie") sessionId: String,
        @Field("EMail") EMail: String,
        @Field("branchId") branchId: String,
        @Field("eventId") eventId: String,
        @Field("gender") gender: String,
        @Field("name") name: String,
        @Field("phoneNumber") phoneNumber: String,
        @Field("regionId") regionId: String
    ): Observable<RegisterResponse>


}