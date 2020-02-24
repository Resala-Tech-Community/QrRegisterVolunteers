/*
 * Created by  Mobile Dev Team  on 1/11/20 9:35 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.network


import com.resala.mobile.qrregister.shared.data.model.EventPOJO
import com.resala.mobile.qrregister.shared.data.model.NormalResponse
import com.resala.mobile.qrregister.ui.eventdetailsfragment.GenderEnum
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

@JvmSuppressWildcards
interface ApiInterface {

    @Multipart
    @POST("login")
    fun logIn(
        @Part("username") username: RequestBody,
        @Part("password") password: RequestBody
    ): Observable<Any>

    @GET("logout")
    fun logOut(
        @Header("cookie") sessionId: String
    ): Observable<String>

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
    ): Observable<NormalResponse>


    @FormUrlEncoded
    @POST("registration")
    fun registerVolunteerByData(
        @Header("cookie") sessionId: String,
        @Field("EMail") EMail: String,
        @Field("branchId") branchId: String,
        @Field("eventId") eventId: String,
        @Field("gender") gender: GenderEnum,
        @Field("name") name: String,
        @Field("phoneNumber") phoneNumber: String,
        @Field("regionId") regionId: String
    ): Observable<NormalResponse>


}