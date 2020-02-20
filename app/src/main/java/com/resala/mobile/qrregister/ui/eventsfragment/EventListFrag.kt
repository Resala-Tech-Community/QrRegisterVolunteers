/*
 * Created by  Mobile Dev Team
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.ui.eventsfragment


import Utils
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.resala.mobile.qrregister.R
import com.resala.mobile.qrregister.databinding.FragEventsBinding
import com.resala.mobile.qrregister.shared.data.model.EventPOJO
import com.resala.mobile.qrregister.shared.ui.frag.BaseFrag
import com.resala.mobile.qrregister.shared.util.RecyclerSectionItemDecoration
import com.resala.mobile.qrregister.shared.util.RecyclerSectionItemDecoration.SectionCallback
import com.resala.mobile.qrregister.shared.util.ext.showError
import org.koin.android.viewmodel.ext.android.viewModel


open class EventListFrag : BaseFrag<EventListVm>() {

    override val vm: EventListVm by viewModel()
    override var layoutId: Int = R.layout.frag_events
    private var mEventList: ArrayList<EventPOJO>? = null
    private var eventadapter: EventsAdapter<EventPOJO>? = null
    private lateinit var viewDataBinding: FragEventsBinding
    var flagDecoration = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragEventsBinding.inflate(inflater, container, false).apply {
            viewmodel = vm
        }

        return viewDataBinding.root
    }


    override fun doOnViewCreated() {
        super.doOnViewCreated()
        viewDataBinding.toolbar.inflateMenu(R.menu.logout_menu)
        viewDataBinding.toolbar.menu.findItem(R.id.logout).setOnMenuItemClickListener {
            showLogout()
            return@setOnMenuItemClickListener true
        }
        checkValidation()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Set the lifecycle owner to the lifecycle of the view
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner


    }

    private fun checkValidation() {

        if (Utils.isOnline(activity())) {
            flagDecoration = false
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
                        setUpStickyHeaders(mEventList)
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


    private fun setUpStickyHeaders(mEventList: ArrayList<EventPOJO>?) {

        val headers: MutableMap<String, Int> = mutableMapOf()
        mEventList?.forEachIndexed { index, eventPOJO ->
            if (!headers.containsKey(eventPOJO.date))
                headers[eventPOJO.date] = index
        }


        val sectionItemDecoration =
            RecyclerSectionItemDecoration(
                resources.getDimensionPixelSize(R.dimen._40sdp),
                true,  // true for sticky, false for not
                object : SectionCallback {
                    override fun isSection(position: Int): Boolean {
                        return (headers.values.contains(position))
                    }

                    override fun getSectionHeader(position: Int): CharSequence {
                        return mEventList?.get(position)
                            ?.date!!.subSequence(0, mEventList[position].date.length)

                    }
                })



        if (!flagDecoration) {
            viewDataBinding.eventsList.addItemDecoration(sectionItemDecoration)
            flagDecoration = true
        }

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


    fun showLogout() {

        val dialogBuilder = AlertDialog.Builder(activity())
        // ...Irrelevant code for customizing the buttons and title
        val inflater = activity()!!.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_logout, null)
        dialogBuilder.setView(dialogView)

        val tv_confirm = dialogView.findViewById(R.id.tv_logout_confirm) as TextView
        val tv_cancle = dialogView.findViewById(R.id.tv_logout_cancel) as TextView


        val alertDialog = dialogBuilder.create()
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.window!!.attributes.windowAnimations = R.style.DialougAnimation
        alertDialog.show()

        tv_confirm.setOnClickListener {
            alertDialog.dismiss()
            //doLogout()
        }

        tv_cancle.setOnClickListener { alertDialog.dismiss() }

    }


}

