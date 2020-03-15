/*
 * Created by  Mobile Dev Team  on 1/12/20 12:10 PM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.resala.mobile.qrregister.R


class DialogScanSuccessFragment : DialogFragment() {

    private var root_view: View? = null
    private var NAME = ""
    private var EVENT = ""
    private var QRCODE = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            NAME = arguments?.getString("NAME")!!
            EVENT = arguments?.getString("EVENT")!!
            QRCODE = arguments?.getString("QRCODE")!!
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root_view = inflater.inflate(R.layout.dialog_scan_success, container, false)
        (root_view!!.findViewById<View>(R.id.fab) as FloatingActionButton).setOnClickListener { dismiss() }

        return root_view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val event = root_view!!.findViewById<TextView>(R.id.event)
        val volunteer = root_view!!.findViewById<TextView>(R.id.volunteer)
        event.text = EVENT
        volunteer.text = NAME


    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }


}