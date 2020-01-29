/*
 * Created by  Mobile Dev Team  on 1/11/20 9:35 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.vm


import androidx.annotation.CallSuper
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.resala.mobile.qrregister.shared.data.DataManager
import com.resala.mobile.qrregister.shared.databases.DBRepository
import com.resala.mobile.qrregister.shared.network.ApiRepository
import com.resala.mobile.qrregister.shared.rx.SchedulerProvider
import com.resala.mobile.qrregister.shared.ui.view.BaseView
import com.resala.mobile.qrregister.shared.util.SharedPref
import com.resala.mobile.qrregister.shared.util.io.app.MyApp
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel(dm: DataManager)
    : ViewModel() {

    lateinit var view: BaseView
    var pref: SharedPref = dm.pref
    var api: ApiRepository = dm.api
    var database: DBRepository = dm.database
    var scheduler: SchedulerProvider = dm.schedulerProvider


    val disposables = CompositeDisposable()

    fun launch(job: () -> Disposable) {
        disposables.add(job())
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    protected fun activity(): FragmentActivity? {
        return view.activity()
    }


    protected fun getString(@StringRes res: Int): String {
        return MyApp.context.getString(res)
    }


    fun loading(isLoading: Boolean) {
        if (isLoading) {
            view.showLoading()
            return
        }
        view.hideLoading()
    }


}
