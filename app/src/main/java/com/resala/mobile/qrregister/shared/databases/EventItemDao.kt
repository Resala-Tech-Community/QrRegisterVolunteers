/*
 * Created by  Mobile Dev Team  on 1/11/20 9:35 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.databases

import androidx.room.*
import com.resala.mobile.qrregister.shared.data.model.EventPOJO


@Dao
interface EventItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: EventPOJO)

    @Update
    fun updateItem(item: EventPOJO)

    @Delete
    fun deleteItem(item: EventPOJO)

    @Query("SELECT * FROM EventPOJO WHERE id == :id")
    fun getItemById(id: Int): EventPOJO

    @Query("SELECT * FROM EventPOJO")
    fun getItems(): List<EventPOJO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertALLItems(items: MutableList<EventPOJO>): List<Long>

    @Query("DELETE FROM EventPOJO")
    fun deletePosts()

}