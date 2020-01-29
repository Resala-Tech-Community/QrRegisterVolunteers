/*
 * Created by  Mobile Dev Team  on 1/11/20 10:57 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.ui.frag

import android.content.BroadcastReceiver
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.resala.mobile.qrregister.shared.ui.activity.BaseActivity
import com.resala.mobile.qrregister.shared.ui.view.BaseView
import com.resala.mobile.qrregister.shared.vm.BaseViewModel

import java.util.*

abstract class BaseFrag<VM : BaseViewModel> : Fragment(), BaseView {

    abstract val vm: VM

    abstract var layoutId: Int
    protected open fun setupUi() {}
    protected open fun setupFont() {}
    protected open fun doOnViewCreated() {}
    protected fun doOnResume() {}

    open var hasBackNavigation = false
    open var hasSwipeRefresh = false

    /*Recycler View Data*/

    protected var mList: ArrayList<Any>? = null

    protected var loadMore: Boolean = false
    protected var offset: Int? = 0
    /*Search passing text handling */
    open var mRegistrationBroadcastReceiver: BroadcastReceiver? = null
    open var text: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            vm.view = this

        } catch (e: Exception) {

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            setupUi()
            setupFont()
            doOnViewCreated()

        } catch (e: Exception) {

        }

    }

    /**
     * Designed to be overridden in Fragments that implement [HasSwipeRefresh]
     */
    protected open fun onSwipeRefresh() {}

    protected open fun onRetryClicked() {}


    override fun onResume() {
        super.onResume()
        try {
            doOnResume()
        } catch (e: Exception) {

        }
    }

    override fun activity(): BaseActivity<*>? {
        return activity as? BaseActivity<*>
    }

    override fun fragment(): BaseFrag<*> {
        return this
    }

    override fun baseViewModel(): BaseViewModel? {
        return vm
    }


}