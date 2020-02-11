/*
 * Created by  Mobile Dev Team  on 1/11/20 9:35 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.network


import com.resala.mobile.qrregister.shared.data.model.EventPOJO
import com.resala.mobile.qrregister.shared.data.model.NormalResponse
import io.reactivex.Observable


class ApiRepository(private val api: ApiInterface) {

    fun login(id: String, password: String): Observable<NormalResponse> {
        return api.logIn(id, password)
    }

    fun getEvents(): Observable<List<EventPOJO>> {
        return api.getEvents()
    }


}
