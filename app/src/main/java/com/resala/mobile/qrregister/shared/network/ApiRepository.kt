/*
 * Created by  Mobile Dev Team  on 1/11/20 9:35 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.network


import com.resala.mobile.qrregister.shared.data.model.EventPOJO
import com.resala.mobile.qrregister.shared.data.model.RegisterResponse
import io.reactivex.Observable
import okhttp3.Credentials
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response


class ApiRepository(private val api: ApiInterface) {

    fun login(username: String, password: String): Observable<Response<ResponseBody>> {
        val auth = Credentials.basic(username, password)
        return api.logIn(auth)
    }

    fun logout(session_id: String): Observable<Response<ResponseBody>> {
        return api.logOut(session_id)
    }

    fun getEvents(session_id: String): Observable<List<EventPOJO>> {
        return api.getEvents(session_id)
    }

    fun getEventById(id: String): Observable<EventPOJO> {
        return api.getEventById(id)
    }

    fun registerVolunteerByCode(
        session_id: String,
        branchId: String,
        code: String?,
        eventId: String,
        phone: String?
    ): Observable<RegisterResponse> {
        return api.registerVolunteerByCode(
            session_id,
            branchId,
            code,
            eventId,
            phone
        )
    }

    fun registerVolunteerByData(
        session_id: String,
        EMail: String,
        branchId: String,
        eventId: String,
        gender: String,
        name: String,
        phoneNumber: String,
        regionId: String
    ): Observable<RegisterResponse> {
        return api.registerVolunteerByData(
            session_id,
            EMail,
            branchId,
            eventId,
            gender,
            name,
            phoneNumber,
            regionId
        )
    }

}
