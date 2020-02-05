/*
 * Created by  Mobile Dev Team  on 2/5/20 8:47 PM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.ui.barCode.barcodedetection

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/** Information about a barcode field.  */
@Parcelize
data class BarcodeField(val label: String, val value: String) : Parcelable
