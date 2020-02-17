/*
 * Created by  Mobile Dev Team  on 1/11/20 9:35 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.network


import com.resala.mobile.qrregister.shared.data.model.EventPOJO
import com.resala.mobile.qrregister.shared.data.model.NormalResponse
import io.reactivex.Observable


class ApiRepository(private val api: ApiInterface,private val api2: ApiInterface2) {

    fun login(id: String, password: String): Observable<NormalResponse> {
        return api.logIn(id, password)
    }

    fun getEvents(): Observable<List<EventPOJO>> {
        return api.getEvents()
    }

    fun getEventById(id: String): Observable<EventPOJO> {
        return api.getEventById(id)
    }

    fun registerVolunteerByCode(
        branchId: String,
        code: String,
        eventId: String,
        phone: String
    ): Observable<NormalResponse> {
        return api2.registerVolunteerByCode(
            branchId,
            code,
            eventId,
            phone
        )
    }

    fun registerVolunteerByData(
        EMail: String,
        branchId: String,
        eventId: String,
        gender: String,
        name: String,
        phoneNumber: String,
        regionId: String
    ): Observable<NormalResponse> {
        return api2.registerVolunteerByData(
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
