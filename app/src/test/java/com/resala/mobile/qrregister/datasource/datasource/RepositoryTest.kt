/*
 * Created by  Mobile Dev Team  on 1/11/20 9:35 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.datasource.datasource

import com.resala.mobile.qrregister.datasource.di.testApp
import com.resala.mobile.qrregister.shared.network.ApiRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject

class RepositoryTest : AutoCloseKoinTest() {

    val repository by inject<ApiRepository>()

    @Before
    fun before() {
        startKoin { modules(testApp) }
    }


    @Test
    fun testCachedSearch() {

        val events1 = repository.getEvents().blockingFirst()
        val events2 = repository.getEvents().blockingFirst()
        assertEquals(events1, events2)
    }

    @Test
    fun testGetPostsSuccess() {
        repository.getEvents().test()
        val test = repository.getEvents().test()
        test.awaitTerminalEvent()
        test.assertComplete()
    }

    @Test
    fun testGetPostsFailed() {
        val test = repository.getEvents().test()
        test.awaitTerminalEvent()
        test.assertValue { list -> list.isEmpty() }
    }
}