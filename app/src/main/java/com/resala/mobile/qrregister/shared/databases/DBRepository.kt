/*
 * Created by  Mobile Dev Team  on 1/11/20 9:35 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.databases


class DBRepository(private val db: AppDatabase) {

    fun getEventDao(): EventItemDao? {
        return db.eventItemDao()
    }



}
