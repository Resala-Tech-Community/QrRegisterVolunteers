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

        getEventList()

    }

    private fun getEventList() {
        vm.responseBody.observe(
            this,
            Observer {
                //Here Travers Through Model Values
            }
        )


        vm.getEvents()

    }


}