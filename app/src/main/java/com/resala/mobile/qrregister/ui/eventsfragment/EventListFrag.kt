/*
 * Created by  Mobile Dev Team
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.ui.eventsfragment


import Utils
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.resala.mobile.qrregister.R
import com.resala.mobile.qrregister.databinding.FragEventsBinding
import com.resala.mobile.qrregister.shared.data.model.EventPOJO
import com.resala.mobile.qrregister.shared.ui.frag.BaseFrag
import com.resala.mobile.qrregister.shared.util.ext.showError
import org.koin.android.viewmodel.ext.android.viewModel

open class EventListFrag : BaseFrag<EventListVm>() {

    override val vm: EventListVm by viewModel()
    override var layoutId: Int = R.layout.frag_events
    private var mEventList: ArrayList<EventPOJO>? = null
    private var eventadapter: EventsAdapter<EventPOJO>? = null
    private lateinit var viewDataBinding: FragEventsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragEventsBinding.inflate(inflater, container, false).apply {
            viewmodel = vm
        }

        setHasOptionsMenu(true)
        return viewDataBinding.root
    }


    override fun doOnViewCreated() {
        super.doOnViewCreated()
        checkValidation()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Set the lifecycle owner to the lifecycle of the view
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner


    }

    private fun checkValidation() {

        if (Utils.isOnline(activity())) {
            setupListAdapter()
            getEventList()

        } else {
            showOfflineMode()
        }
    }

    private fun getEventList() {
        vm.responseBody.observe(
            this,
            Observer {
                when {
                    it.isLoading -> {
                        showLoading()
                        showLoader()
                    }
                    it.eventList.isNotEmpty() -> {
                        showMainLayout()
                        mEventList?.clear()
                        mEventList?.addAll(it.eventList)
                        eventadapter!!.notifyDataSetChanged()
                        hideLoading()
                    }
                    it.errorMessage != null -> {
                        showErrorData()
                        hideLoading()
                        it.errorMessage.showError(context()!!)
                    }
                    it.isOffline -> {
                        hideLoading()
                        showOfflineMode()
                    }

                }
            }
        )
        vm.getEvents()

    }


    private fun setupListAdapter() {
        mEventList = ArrayList()
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            eventadapter = EventsAdapter(mEventList!!, ::onEventClicked)
            viewDataBinding.eventsList.adapter = eventadapter
        }
    }

    override fun onRetryClicked() {
        super.onRetryClicked()
        checkValidation()
    }


    private fun onEventClicked(event: EventPOJO) {

        val action = EventListFragDirections.actionEventsFragToEventDetailsFrag(
            event.id.toString(),
            event.name
        )
        if (findNavController().currentDestination?.id == R.id.eventsFrag) {
            findNavController().navigate(action)
        }
    }


}

