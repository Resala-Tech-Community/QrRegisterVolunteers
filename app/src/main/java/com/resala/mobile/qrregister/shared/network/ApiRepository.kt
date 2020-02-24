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


class ApiRepository(private val api: ApiInterface) {

    fun login(username: RequestBody, password: RequestBody): Observable<Any> {
        return api.logIn(username, password)
    }

    fun logout(session_id: String): Observable<String> {
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
        code: String,
        eventId: String,
        phone: String
    ): Observable<NormalResponse> {
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
        gender: GenderEnum,
        name: String,
        phoneNumber: String,
        regionId: String
    ): Observable<NormalResponse> {
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
