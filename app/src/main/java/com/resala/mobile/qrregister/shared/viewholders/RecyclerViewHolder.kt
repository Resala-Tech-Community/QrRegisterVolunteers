/*
 * Created by  Mobile Dev Team  on 1/11/20 9:35 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.resala.mobile.qrregister.shared.vm.BaseViewModel


abstract class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun onBindView(`object`: Any, position: Int, viewModel: BaseViewModel)


}