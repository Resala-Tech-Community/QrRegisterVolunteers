/*
 * Created by  Mobile Dev Team  on 1/11/20 6:22 PM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.ui.eventdetailsfragment

import Utils
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.database.*
import com.google.zxing.Result
import com.jakewharton.rxbinding2.widget.RxTextView
import com.resala.mobile.qrregister.R
import com.resala.mobile.qrregister.databinding.FragEventDetailsBinding
import com.resala.mobile.qrregister.shared.data.model.EventPOJO
import com.resala.mobile.qrregister.shared.data.model.Region
import com.resala.mobile.qrregister.shared.data.model.RegisterResponse
import com.resala.mobile.qrregister.shared.dialogs.DialogScanSuccessFragment
import com.resala.mobile.qrregister.shared.ui.frag.BaseFrag
import com.resala.mobile.qrregister.shared.util.FlashbarUtil
import com.resala.mobile.qrregister.shared.util.ext.showError
import com.resala.mobile.qrregister.shared.util.isNullOrEmpty
import com.resala.mobile.qrregister.shared.util.isPhoneNumber
import com.resala.mobile.qrregister.shared.vm.SharedViewModel
import com.robinhood.ticker.TickerUtils
import kotlinx.android.synthetic.main.sheet_new_vlounteer.view.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.koin.android.viewmodel.ext.android.viewModel


class EventDetailsFrag : BaseFrag<EventDetailsVm>(), ZXingScannerView.ResultHandler {


    override val vm: EventDetailsVm by viewModel()
    override var layoutId: Int = R.layout.frag_event_details
    private var mScannerView: ZXingScannerView? = null
    private lateinit var viewDataBinding: FragEventDetailsBinding
    private var mBehavior: BottomSheetBehavior<*>? = null
    private var mFirebaseDatabase: DatabaseReference? = null
    private var mFirebaseInstance: FirebaseDatabase? = null
    private var event: EventPOJO? = null
    var regionId = 0
    var isRegionSelected = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        viewDataBinding = FragEventDetailsBinding.inflate(inflater, container, false).apply {
            viewmodel = vm
        }

        // Set the lifecycle owner to the lifecycle of the view
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        viewDataBinding.executePendingBindings()


        return viewDataBinding.root
    }

    override fun doOnViewCreated() {
        super.doOnViewCreated()

        //to get the shared data between fragments
        val model = activity()?.let { ViewModelProviders.of(it).get(SharedViewModel::class.java) }
        model?.selected?.observe(this, Observer<EventPOJO> {
            event = it
            setToolBar()
            initView()
            setupRegisterField()
            checkValidations()

        })
    }

    private fun checkValidations() {

        mFirebaseInstance = FirebaseDatabase.getInstance()
        mFirebaseDatabase = mFirebaseInstance?.reference
            ?.child(event?.branchId.toString())?.child(event?.eventId.toString())

        viewDataBinding.run {
            tickerView.setCharacterLists(TickerUtils.provideNumberList())
            tickerView.animationDuration = 600
        }
        mFirebaseDatabase?.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(dataError: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val map =
                    dataSnapshot.value as Map<String, Any>?
                viewDataBinding.tickerView.text = map?.get("count").toString()

            }

        })

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(
                    activity()!!,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                startCamera()
            } else {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), PERMISSION_CODE)
            }
        } else {
            startCamera()
        }
    }

    private fun initView() {

        mScannerView = ZXingScannerView(activity)
        viewDataBinding.contentFrame.addView(mScannerView)
        mBehavior = BottomSheetBehavior.from<View>(viewDataBinding.root.new_volunteer_sheet)
        showBottomSheetDialog()

        vm.responseRegisterBody.observe(
            this,
            Observer {
                when {
                    it.isLoading -> {
                        vm.showHideDots(true)
                    }
                    it.result != null -> {

                        vm.toggleRevealView(false)
                        vm.showHideDots(false)
                        showSuccessDialog(it.result)

                    }
                    it.errorMessage != null -> {

                        vm.showHideDots(false)
                        it.errorMessage.showError(context()!!)
                    }
                    it.isOffline -> {

                    }

                }
            }
        )


    }

    private fun setToolBar() {
        viewDataBinding.toolbar.title = event?.name
        viewDataBinding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        viewDataBinding.toolbar.setNavigationOnClickListener { activity()!!.onBackPressed() }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun handleResult(rawResult: Result?) {

        Log.e("QrResult", rawResult?.text.toString())
        registerVolunteerApi(rawResult!!)
        // Note:
        // * Wait 2 seconds to resume the preview.
        // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
        // * I don't know why this is the case but I don't have the time to figure out.
        Handler().postDelayed({
            mScannerView!!.resumeCameraPreview(this)
        }, 200)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun registerVolunteerApi(rawResult: Result) {

        vm.registerVolunteerByCodeOrNumber(
            vm.pref.session,
            event?.branchId.toString(),
            rawResult.text,
            event?.eventId.toString(),
            null
        )
    }

    private fun showSuccessDialog(result: RegisterResponse) {
        val fragmentManager = childFragmentManager
        val newFragment = DialogScanSuccessFragment()
        val args = Bundle()
        args.putString("NAME", result.name)
        args.putString("EVENT", result.phoneNumber)
        args.putString("QRCODE", result.code)

        newFragment.arguments = args
        val transaction = fragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.add(R.id.dialogFrame, newFragment).addToBackStack(null)
            .commitAllowingStateLoss()
    }

    private fun startCamera() {
        mScannerView!!.setResultHandler(this)
        mScannerView?.setAutoFocus(true)
        mScannerView!!.startCamera()
    }


    private fun resumeCamera() {
        mScannerView!!.setResultHandler(this)
        mScannerView?.setAutoFocus(true)
        mScannerView!!.resumeCameraPreview(this)
    }

    override fun onResume() {
        super.onResume()
        resumeCamera()

    }


    override fun onPause() {
        super.onPause()
        stopCamera()
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera()
            } else {
                stopCamera()
                FlashbarUtil.show(
                    getString(R.string.please_grant_camera_permission),
                    activity = activity()!!
                )

            }
        }
    }

    private fun stopCamera() {
        mScannerView!!.setResultHandler(this)
        mScannerView!!.stopCamera()
    }

    private fun showBottomSheetDialog() {


        if (mBehavior!!.state == BottomSheetBehavior.STATE_EXPANDED) {
            mBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        vm.toggleSheet.observe(this, Observer {
            mBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
        })


        mBehavior!!.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        vm.showHideText(true)

                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        vm.showHideText(false)
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        vm.showHideText(true)

                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                    }
                }
            }
        })
    }

    @SuppressLint("CheckResult")
    private fun setupRegisterField() {
        //spinner region
        vm.getRegions(vm.pref.session)
        vm.responseRegionsBody.observe(
            this,
            Observer {
                when {
                    it.isLoading -> {
                        vm.showHideDots(true)
                    }
                    it.result != null -> {
                        vm.showHideDots(false)
                        setRegionsAdapter(it.result)
                    }
                    it.errorMessage != null -> {
                        vm.showHideDots(false)
                        it.errorMessage.showError(context()!!)
                    }
                    it.isOffline -> {

                    }

                }
            }
        )
        RxTextView.textChanges(viewDataBinding.etId).subscribe { text ->
            registerCodeBtnVisibilty(text)
        }

        RxTextView.textChanges(viewDataBinding.etNumber).subscribe { text ->

            registerNumberBtnVisibilty(text)

        }
        viewDataBinding.btnSendCodeOrID.setOnClickListener {

            vm.registerVolunteerByCodeOrNumber(
                vm.pref.session,
                event?.branchId.toString(),
                viewDataBinding.etId.text.toString(),
                event?.eventId.toString(),
                null
            )

        }

        viewDataBinding.btnSendNumber.setOnClickListener {
            vm.registerVolunteerByCodeOrNumber(
                vm.pref.session,
                event?.branchId.toString(),
                null,
                event?.eventId.toString(),
                viewDataBinding.etNumber.text.toString()
            )
        }



        viewDataBinding.newVolunteerSheet.btnRegisterData.setOnClickListener {
            //introduce data validation before registering
            checkDataValidations()

        }


    }

    private fun setRegionsAdapter(result: ArrayList<Region>) {
        val regionPromptMessage = Region()
        regionPromptMessage.name = getString(R.string.choose_a_region_from_the_list)
        result.add(regionPromptMessage)

        val adapter = RegionAdapter(activity()!!, result)
        viewDataBinding.newVolunteerSheet.spinnerRegions.adapter = adapter
        viewDataBinding.newVolunteerSheet.spinnerRegions.setSelection(adapter.count)
        viewDataBinding.newVolunteerSheet.spinnerRegions.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    val region = parent!!.getItemAtPosition(0) as Region
                    regionId = region.id
                    isRegionSelected = false
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    isRegionSelected = true
                    val region = parent!!.getItemAtPosition(position) as Region
                    regionId = region.id

                }

            }

    }

    fun checkDataValidations() {
        var focusView: View? = null
        var cancel = false


        if (isNullOrEmpty(viewDataBinding.newVolunteerSheet.etEmail.text.toString())) {

            viewDataBinding.newVolunteerSheet.etInputEmail.error =
                getString(R.string.required_field)
            focusView = viewDataBinding.newVolunteerSheet.etEmail
            cancel = true
        } else {
            viewDataBinding.newVolunteerSheet.etInputEmail.error = null
        }

        if (!Utils.isEmailValidDefault(viewDataBinding.newVolunteerSheet.etEmail.text.toString())) {
            viewDataBinding.newVolunteerSheet.etInputEmail.error =
                getString(R.string.invalid_email_format)
            focusView = viewDataBinding.newVolunteerSheet.etEmail
            cancel = true
        } else {
            viewDataBinding.newVolunteerSheet.etInputEmail.error = null
        }



        if (isNullOrEmpty(viewDataBinding.newVolunteerSheet.etName.text.toString())) {
            viewDataBinding.newVolunteerSheet.edInputName.error = getString(R.string.required_field)
            focusView = viewDataBinding.newVolunteerSheet.etName
            cancel = true
        } else {
            viewDataBinding.newVolunteerSheet.edInputName.error = null
        }


        if (isNullOrEmpty(viewDataBinding.newVolunteerSheet.etPhoneNumber.text.toString())) {
            viewDataBinding.newVolunteerSheet.edInputPhoneId.error =
                getString(R.string.required_field)
            focusView = viewDataBinding.newVolunteerSheet.etPhoneNumber
            cancel = true
        } else {
            viewDataBinding.newVolunteerSheet.edInputPhoneId.error = null
        }

        if (!isPhoneNumber(viewDataBinding.newVolunteerSheet.etPhoneNumber.text.toString())) {
            viewDataBinding.newVolunteerSheet.edInputPhoneId.error =
                getString(R.string.not_valid_mobile)
            focusView = viewDataBinding.newVolunteerSheet.etPhoneNumber
            cancel = true
        } else {
            viewDataBinding.newVolunteerSheet.edInputPhoneId.error = null
        }


        if (cancel) {
            focusView?.requestFocus()
        } else if (!isRegionSelected) {
            FlashbarUtil.show(
                getString(R.string.choose_a_region_from_the_list),
                activity = activity()!!
            )
        } else {


            val gender =
                if (viewDataBinding.newVolunteerSheet.spinnerGender.selectedItemPosition == 0) "male" else "female"
            vm.registerVolunteerByData(
                vm.pref.session,
                viewDataBinding.newVolunteerSheet.etEmail.text.toString(),
                event?.branchId.toString(),
                event?.eventId.toString(),
                gender,
                viewDataBinding.newVolunteerSheet.etName.text.toString(),
                viewDataBinding.newVolunteerSheet.etPhoneNumber.text.toString(),
                regionId.toString()

            )
        }
    }

    private fun registerNumberBtnVisibilty(text: CharSequence?) {
        viewDataBinding.btnSendNumber.error = null
        if (text.isNullOrEmpty())
            viewDataBinding.btnSendNumber.visibility = View.GONE
        else
            viewDataBinding.btnSendNumber.visibility = View.VISIBLE
    }

    private fun registerCodeBtnVisibilty(text: CharSequence?) {
        if (text.isNullOrEmpty())
            viewDataBinding.btnSendCodeOrID.visibility = View.GONE
        else
            viewDataBinding.btnSendCodeOrID.visibility = View.VISIBLE
    }

    companion object {
        private var PERMISSION_CODE = 123
    }
}


