/*
 * Created by  Mobile Dev Team  on 2/5/20 8:44 PM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.ui.barCode


import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Intent
import android.hardware.Camera
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.common.internal.Objects
import com.google.android.material.chip.Chip
import com.google.firebase.FirebaseApp
import com.resala.mobile.qrregister.ui.barCode.camera.GraphicOverlay
import com.resala.mobile.qrregister.ui.barCode.barViewModel.WorkflowModel
import com.resala.mobile.qrregister.ui.barCode.barViewModel.WorkflowModel.WorkflowState
import com.resala.mobile.qrregister.ui.barCode.barcodedetection.BarcodeField
import com.resala.mobile.qrregister.ui.barCode.barcodedetection.BarcodeProcessor
import com.resala.mobile.qrregister.ui.barCode.barcodedetection.BarcodeResultFragment
import com.resala.mobile.qrregister.ui.barCode.camera.CameraSourcePreview
import com.resala.mobile.qrregister.R
import com.resala.mobile.qrregister.shared.util.io.app.MyApp.Companion.context
import com.resala.mobile.qrregister.ui.barCode.camera.CameraSource
import com.resala.mobile.qrregister.ui.barCode.settings.SettingsActivity
import java.io.IOException
import java.util.ArrayList

/** Demonstrates the barcode scanning workflow using camera preview.  */
class BarCodeActivity : AppCompatActivity(), OnClickListener {

    private var cameraSource: CameraSource? = null
    private var preview: CameraSourcePreview? = null
    private var graphicOverlay: GraphicOverlay? = null
    private var settingsButton: View? = null
    private var flashButton: View? = null
    private var promptChip: Chip? = null
    private var promptChipAnimator: AnimatorSet? = null
    private var workflowModel: WorkflowModel? = null
    private var currentWorkflowState: WorkflowState? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode)
        FirebaseApp.initializeApp(context)
        preview = findViewById(R.id.camera_preview)
        graphicOverlay = findViewById<GraphicOverlay>(R.id.camera_preview_graphic_overlay).apply {
            setOnClickListener(this@BarCodeActivity)
            cameraSource = CameraSource(this)
        }

        promptChip = findViewById(R.id.bottom_prompt_chip)
        promptChipAnimator =
            (AnimatorInflater.loadAnimator(this, R.animator.bottom_prompt_chip_enter) as AnimatorSet).apply {
                setTarget(promptChip)
            }

        findViewById<View>(R.id.close_button).setOnClickListener(this)
        flashButton = findViewById<View>(R.id.flash_button).apply {
            setOnClickListener(this@BarCodeActivity)
        }
        settingsButton = findViewById<View>(R.id.settings_button).apply {
            setOnClickListener(this@BarCodeActivity)
        }

        setUpWorkflowModel()
    }

    override fun onResume() {
        super.onResume()
        workflowModel?.markCameraFrozen()
        settingsButton?.isEnabled = true
        currentWorkflowState = WorkflowState.NOT_STARTED
        cameraSource?.setFrameProcessor(BarcodeProcessor(graphicOverlay!!, workflowModel!!))
        workflowModel?.setWorkflowState(WorkflowState.DETECTING)
    }

    override fun onPostResume() {
        super.onPostResume()
        BarcodeResultFragment.dismiss(supportFragmentManager)
    }



    override fun onPause() {
        super.onPause()
        currentWorkflowState = WorkflowState.NOT_STARTED
        stopCameraPreview()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraSource?.release()
        cameraSource = null
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.close_button -> /*onBackPressed()*/finish()
            R.id.flash_button -> {
                flashButton?.let {
                    if (it.isSelected) {
                        it.isSelected = false
                        cameraSource?.updateFlashMode(Camera.Parameters.FLASH_MODE_OFF)
                    } else {
                        it.isSelected = true
                        cameraSource!!.updateFlashMode(Camera.Parameters.FLASH_MODE_TORCH)
                    }
                }
            }
            R.id.settings_button -> {
                settingsButton?.isEnabled = false
                startActivity(Intent(this, SettingsActivity::class.java))
            }
        }
    }

    private fun startCameraPreview() {
        val workflowModel = this.workflowModel ?: return
        val cameraSource = this.cameraSource ?: return
        if (!workflowModel.isCameraLive) {
            try {
                workflowModel.markCameraLive()
                preview?.start(cameraSource)
            } catch (e: IOException) {
                Log.e(TAG, "Failed to start camera preview!", e)
                cameraSource.release()
                this.cameraSource = null
            }
        }
    }

    private fun stopCameraPreview() {
        val workflowModel = this.workflowModel ?: return
        if (workflowModel.isCameraLive) {
            workflowModel.markCameraFrozen()
            flashButton?.isSelected = false
            preview?.stop()
        }
    }

    private fun setUpWorkflowModel() {
        workflowModel = ViewModelProvider(this).get(WorkflowModel::class.java)

        // Observes the workflow state changes, if happens, update the overlay view indicators and
        // camera preview state.
        workflowModel!!.workflowState.observe(this, Observer { workflowState ->
            if (workflowState == null || Objects.equal(currentWorkflowState, workflowState)) {
                return@Observer
            }

            currentWorkflowState = workflowState
            val wasPromptChipGone = promptChip?.visibility == View.GONE

            when (workflowState) {
                WorkflowState.DETECTING -> {
                    promptChip?.visibility = View.VISIBLE
                    promptChip?.setText(R.string.prompt_point_at_a_barcode)
                    startCameraPreview()
                }
                WorkflowState.CONFIRMING -> {
                    promptChip?.visibility = View.VISIBLE
                    promptChip?.setText(R.string.prompt_move_camera_closer)
                    startCameraPreview()
                }
                WorkflowState.SEARCHING -> {
                    promptChip?.visibility = View.VISIBLE
                    promptChip?.setText(R.string.prompt_searching)
                    stopCameraPreview()
                }
                WorkflowState.DETECTED, WorkflowState.SEARCHED -> {
                    promptChip?.visibility = View.GONE
                    stopCameraPreview()
                }
                else -> promptChip?.visibility = View.GONE
            }

            val shouldPlayPromptChipEnteringAnimation = wasPromptChipGone && promptChip?.visibility == View.VISIBLE
            promptChipAnimator?.let {
                if (shouldPlayPromptChipEnteringAnimation && !it.isRunning) it.start()
            }
        })

        workflowModel?.detectedBarcode?.observe(this, Observer { barcode ->
            if (barcode != null) {
                val barcodeFieldList = ArrayList<BarcodeField>()
                barcodeFieldList.add(BarcodeField("Raw Value", barcode.rawValue ?: ""))
                BarcodeResultFragment.show(supportFragmentManager, barcodeFieldList)
            }
        })
    }

    companion object {
        private const val TAG = "LiveBarcodeActivity"
    }
}
