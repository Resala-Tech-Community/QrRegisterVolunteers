/*
 * Created by  Mobile Dev Team  on 2/3/20 11:33 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.ui.eventsFragment

import android.renderscript.ScriptGroup
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.resala.mobile.qrregister.R
import com.resala.mobile.qrregister.databinding.ViewEventListItemBinding
import com.resala.mobile.qrregister.shared.data.model.EventPOJO
import com.resala.mobile.qrregister.shared.interfaces.SimpleItemClickListener
import kotlinx.android.synthetic.main.dialog_scan_success.view.*
import java.text.FieldPosition

class EventsAdapter<T>(private val eventList: ArrayList<T>,private val mClickListener :(View)->Unit) : RecyclerView.Adapter<EventsAdapter.EventListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventListViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        val viewBinding =DataBindingUtil.inflate<ViewEventListItemBinding>(layoutInflater,R.layout.view_event_list_item,parent,false)
        return EventListViewHolder(viewBinding)
    }

    override fun getItemCount()=eventList.size

    override fun onBindViewHolder(holder: EventListViewHolder, position: Int) {
        holder.bind(eventList[position], mClickListener)
    }


    class EventListViewHolder(var view:ViewEventListItemBinding):RecyclerView.ViewHolder(view.root){

        fun <T>bind(bindObj:T,clickListener:(View)->Unit){
            if(bindObj is EventPOJO){

                view.event=bindObj

                view.root.setOnClickListener {
                    clickListener(it)
                }
            }
        }
    }

}



