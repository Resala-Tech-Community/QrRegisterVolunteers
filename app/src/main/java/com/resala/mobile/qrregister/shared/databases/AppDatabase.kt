/*
 * Created by  Mobile Dev Team  on 1/11/20 9:35 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.resala.mobile.qrregister.shared.data.model.EventPOJO


@Database(entities = [EventPOJO::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun eventItemDao(): EventItemDao


}