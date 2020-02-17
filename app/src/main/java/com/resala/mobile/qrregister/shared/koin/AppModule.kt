/*
 * Created by  Mobile Dev Team  on 1/11/20 9:35 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.koin


import androidx.preference.PreferenceManager
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.resala.mobile.qrregister.BuildConfig
import com.resala.mobile.qrregister.shared.data.DataManager
import com.resala.mobile.qrregister.shared.databases.AppDatabase
import com.resala.mobile.qrregister.shared.databases.DBRepository
import com.resala.mobile.qrregister.shared.network.ApiInterface
import com.resala.mobile.qrregister.shared.network.ApiInterface2
import com.resala.mobile.qrregister.shared.network.ApiRepository
import com.resala.mobile.qrregister.shared.rx.SchedulerProvider
import com.resala.mobile.qrregister.shared.rx.SchedulerProviderImpl
import com.resala.mobile.qrregister.shared.util.BasicAuthInterceptor
import com.resala.mobile.qrregister.shared.util.SharedPref
import com.resala.mobile.qrregister.shared.util.io.ReceivedCookiesInterceptor
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val appModule = module {

    // ApiInterface
    single {

        val gson = GsonBuilder()
            .setLenient()
            .create()

        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
            .create(ApiInterface::class.java)
    }

    // ApiInterface2
    single {

        val gson = GsonBuilder()
            .setLenient()
            .create()

        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL_TWO)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
            .create(ApiInterface2::class.java)
    }


    // OkHttpClient
    single {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY


        val builder = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(BasicAuthInterceptor("admin", "admin"))
            .addInterceptor(ReceivedCookiesInterceptor())
            .addNetworkInterceptor(BasicAuthInterceptor("admin", "admin"))
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
        builder.build()
    }

    single { DataManager(get(), get(), get(), get()) }

    single { ApiRepository(get(),get()) }

    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "resala-db")
            .build()
    }

    single { DBRepository(get()) }

    single { SharedPref(get()) }

    // default pref
    single { PreferenceManager.getDefaultSharedPreferences(androidContext()) }

    single<SchedulerProvider> { SchedulerProviderImpl() }


}