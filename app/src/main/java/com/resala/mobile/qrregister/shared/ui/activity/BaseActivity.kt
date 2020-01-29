/*
 * Created by  Mobile Dev Team  on 1/11/20 9:35 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.ui.activity


import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.multidex.MultiDex
import com.resala.mobile.qrregister.R
import com.resala.mobile.qrregister.shared.data.model.EventPOJO
import com.resala.mobile.qrregister.shared.ui.view.BaseView
import com.resala.mobile.qrregister.shared.util.SharedPref
import com.resala.mobile.qrregister.shared.vm.BaseViewModel
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.util.*


abstract class BaseActivity<VM : BaseViewModel>
    : AppCompatActivity(), BaseView {

    abstract val vm: VM
    lateinit var pref: SharedPref
    abstract var layoutId: Int
    private var mProgressBar: RelativeLayout? = null

    private var mFrameLayout: FrameLayout? = null
    lateinit var view: View
    open var hasSwipeRefresh = false
    open var hasBackNavigation = false
    /*Recycler View Data*/

    protected var mList: ArrayList<EventPOJO>? = null
    protected var loadMore: Boolean = false
    protected var offset: Int? = 0
    protected open fun bindUi() {}
    protected open fun doOnCreate() {}
    protected open fun doOnCreate(savedInstanceState: Bundle?) {}
    protected open fun onSwipeRefresh() {}
    protected open fun onRetryClicked() {}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupTransitionAnimation()


        try {
            vm.view = this
            if (layoutId != 0)
                setContentView(layoutId)

            bindUi()
            doOnCreate()
            doOnCreate(savedInstanceState)

            initializeToolbar()
            enableBackNavigation()

        } catch (e: Exception) {
            //CrashlyticsUtil.logAndPrint(e)
        }

    }

    private fun enableBackNavigation() {
        if (!hasBackNavigation) return
        val toolbar: Toolbar = findViewById(R.id.toolbar)
            ?: throw IllegalStateException("toolbar not found!")
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener { activity().onBackPressed() }
    }


    override fun setContentView(@LayoutRes layoutResID: Int) {
        super.setContentView(R.layout.activity_base)
        initializeView(layoutResID)
    }

    private fun initializeView(layoutResID: Int) {
        view = window.decorView.findViewById(android.R.id.content)
        mFrameLayout = findViewById(R.id.baseFrameLayout)
        mProgressBar = findViewById(R.id.relProgressBar)
        mProgressBar!!.isClickable = true
        val layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mFrameLayout!!.addView(layoutInflater.inflate(layoutResID, null))
    }

    /**
     * Initialize Toolbar
     */
    protected fun initializeToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = resources.getString(R.string.app_name)
    }


    private fun setupTransitionAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale)
        }


    }

    override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {

        } catch (e: Exception) {
            //CrashlyticsUtil.logAndPrint(e)
        }

    }

    override fun context(): Context {
        return this
    }

    override fun activity(): BaseActivity<*> {
        return this
    }

    override fun baseViewModel(): BaseViewModel? {
        return vm
    }


    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
        MultiDex.install(this)
    }

    protected fun showProgressBar() {
        mProgressBar!!.visibility = View.VISIBLE
        mFrameLayout!!.isClickable = false
    }

    protected fun hideProgressBar() {
        mProgressBar!!.visibility = View.GONE
        mFrameLayout!!.isClickable = true
    }



}
