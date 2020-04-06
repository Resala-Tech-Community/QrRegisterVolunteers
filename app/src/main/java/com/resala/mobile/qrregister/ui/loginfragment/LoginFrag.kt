/*
 * Created by  Mobile Dev Team  on 1/11/20 10:49 AM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.ui.loginfragment


import android.app.Activity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.resala.mobile.qrregister.R
import com.resala.mobile.qrregister.databinding.FragLoginBinding
import com.resala.mobile.qrregister.shared.ui.frag.BaseFrag
import com.resala.mobile.qrregister.shared.util.BuildUtil
import com.resala.mobile.qrregister.shared.util.FlashbarUtil
import com.resala.mobile.qrregister.shared.util.isNullOrEmpty
import kotlinx.android.synthetic.main.frag_login.*
import org.koin.android.viewmodel.ext.android.viewModel


class LoginFrag : BaseFrag<LoginVm>() {
    override val vm: LoginVm by viewModel()
    override var layoutId: Int = R.layout.frag_login

    private lateinit var viewDataBinding: FragLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragLoginBinding.inflate(inflater, container, false).apply {
            viewmodel = vm
        }

        setHasOptionsMenu(true)
        return viewDataBinding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Set the lifecycle owner to the lifecycle of the view
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner


    }

    override fun doOnViewCreated() {
        super.doOnViewCreated()
        if (vm.pref.session != "") findNavController().navigate(LoginFragDirections.actionLoginFragToEventsFrag())
        setupLogin()
        debugCredentials()
    }

    private fun debugCredentials() {
        if (!BuildUtil.isDebug()) return
        idEditText.setText("admin")
        passwordEditText.setText("admin")
    }


    private fun setupLogin() {
        viewDataBinding.passwordEditText.transformationMethod = PasswordTransformationMethod()
        vm.login.observe(this, Observer {
            checkValidations(idEditText, passwordEditText)
        })


    }


    fun checkValidations(idEdt: EditText, passwordEdt: EditText) {
        var focusView: View? = null
        var cancel = false


        if (isNullOrEmpty(idEdt.text.toString())) {
            //Take Action
            etInputId.error = getString(R.string.required_field)
            focusView = etInputId
            cancel = true
        } else {
            etInputId.error = null
        }

        if (isNullOrEmpty(passwordEdt.text.toString())) {
            etInputPassword.error = getString(R.string.required_field)
            focusView = etInputPassword
            cancel = true
        } else {
            etInputPassword.error = null
        }

        if (cancel) {
            focusView?.requestFocus()
        } else {

            getLoginResponse()

        }

    }

    private fun getLoginResponse() {

        vm.loginResponse.observe(this, Observer {
            when {
                it.result != null -> {
                    activity()?.hideProgressBar()
                    when (it.result.code()) {
                        in 200..300 -> {
                            //vm.pref.session = "JSESSIONID=1AE1437174DAB9F3E5B27A72C5B9DD61"
                            vm.pref.session = it.result.headers().get("Set-Cookie")!!
                            val action = LoginFragDirections
                                .actionLoginFragToEventsFrag()
                            findNavController().navigate(action)
                        }
                        else -> {

                            FlashbarUtil.show(
                                getString(R.string.invalid_credintials),
                                activity = context as Activity
                            )
                        }
                    }

                }
                it.error != null -> {
                }
                it.isLoading -> {
                    activity()?.showProgressBar()
                }
            }

        })

        vm.login(
            viewDataBinding.idEditText.text.toString(),
            viewDataBinding.passwordEditText.text.toString()
        )


    }
}


