/*
 * Created by  Mobile Dev Team
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.ui.eventsfragment


import androidx.lifecycle.Observer
import com.resala.mobile.qrregister.R
import com.resala.mobile.qrregister.shared.ui.frag.BaseFrag
import org.koin.android.viewmodel.ext.android.viewModel

class EventListFrag : BaseFrag<EventListVm>() {

    override val vm: EventListVm by viewModel()
    override var layoutId: Int = R.layout.activity_base // Replace with list layout


    override fun doOnViewCreated() {
        super.doOnViewCreated()

        loading()
        showError()
        getEventList()

        vm.getEvent()

    }

    private fun loading() {
        vm.load
            .observe(this, Observer {
                //Hide Loading View
            })

    }

    private fun getEventList() {
        vm.eventListMutableLiveData
            .observe(this
                , Observer {
                    //Populate RV List
                })

    }

    private fun showError() {
        vm.errorMessage
            .observe(this,
                Observer {
                    //Show Error Message
                })
    }


}