/*
 * Created by  Mobile Dev Team  on 3/20/20 9:40 PM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.ui.eventdetailsfragment

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.resala.mobile.qrregister.R
import com.resala.mobile.qrregister.shared.data.model.Region
import java.util.*

class RegionAdapter(context: Context, countryList: ArrayList<Region>) :
    ArrayAdapter<Region>(context, 0, countryList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                R.layout.region_spinner_row, parent, false
            )
        }

        val textViewName = convertView!!.findViewById<TextView>(R.id.text_view_name)

        val currentItem = getItem(position)

        if (currentItem != null) {
            textViewName.text = currentItem.name
        }

        return convertView
    }

    override fun getCount(): Int {
        // don't display last item. It is used as hint.
        val count = super.getCount()
        return if (count > 0) count - 1 else count
    }
}