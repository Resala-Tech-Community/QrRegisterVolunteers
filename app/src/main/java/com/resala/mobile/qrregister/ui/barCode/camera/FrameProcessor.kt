/*
 * Created by  Mobile Dev Team  on 2/5/20 8:51 PM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.ui.barCode.camera

import java.nio.ByteBuffer

/** An interface to process the input camera frame and perform detection on it.  */
interface FrameProcessor {

    /** Processes the input frame with the underlying detector.  */
    fun process(data: ByteBuffer, frameMetadata: FrameMetadata, graphicOverlay: GraphicOverlay)

    /** Stops the underlying detector and release resources.  */
    fun stop()
}
