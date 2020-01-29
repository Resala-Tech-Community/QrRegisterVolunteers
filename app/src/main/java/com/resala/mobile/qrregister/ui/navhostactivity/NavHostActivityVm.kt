/*
 * Created by  Mobile Dev Team  on 1/11/20 10:43 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.ui.navhostactivity

import com.resala.mobile.qrregister.shared.data.DataManager
import com.resala.mobile.qrregister.shared.vm.BaseViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val navHostActivityModule = module {
    viewModel { NavHostActivityVm(get()) }
}

class NavHostActivityVm(dataManager: DataManager) : BaseViewModel(dataManager) {


}