/*
 * Created by  Mobile Dev Team  on 1/11/20 9:35 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.util.io


import android.text.TextUtils
import android.widget.ImageView
import com.resala.mobile.qrregister.R
import com.squareup.picasso.Picasso

object ImageSetter {


    fun loadImage(mUrl: String, mImageView: ImageView?) {
        if (!TextUtils.isEmpty(mUrl)) {
            Picasso.get()
                    .load(mUrl)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .fit().centerInside()
                    .into(mImageView)
        } else {
            mImageView?.setImageResource(R.drawable.placeholder)
        }
    }

}