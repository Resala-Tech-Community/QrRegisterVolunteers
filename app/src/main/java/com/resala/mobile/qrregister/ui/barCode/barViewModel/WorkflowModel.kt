/*
 * Created by  Mobile Dev Team  on 2/5/20 10:16 PM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.ui.barCode.barViewModel

import android.app.Application
import androidx.annotation.MainThread
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.resala.mobile.qrregister.ui.barCode.barcodedetection.DetectedObject
import java.util.HashSet

/** View model for handling application workflow based on camera preview.  */
class WorkflowModel(application: Application) : AndroidViewModel(application) {

    val workflowState = MutableLiveData<WorkflowState>()
    val detectedBarcode = MutableLiveData<FirebaseVisionBarcode>()

    private val objectIdsToSearch = HashSet<Int>()

    var isCameraLive = false
        private set

    private var confirmedObject: DetectedObject? = null


    /**
     * State set of the application workflow.
     */
    enum class WorkflowState {
        NOT_STARTED,
        DETECTING,
        DETECTED,
        CONFIRMING,
        CONFIRMED,
        SEARCHING,
        SEARCHED
    }

    @MainThread
    fun setWorkflowState(workflowState: WorkflowState) {
        if (workflowState != WorkflowState.CONFIRMED &&
                workflowState != WorkflowState.SEARCHING &&
                workflowState != WorkflowState.SEARCHED
        ) {
            confirmedObject = null
        }
        this.workflowState.value = workflowState
    }


    fun markCameraLive() {
        isCameraLive = true
        objectIdsToSearch.clear()
    }

    fun markCameraFrozen() {
        isCameraLive = false
    }


}
