package com.cts.common.code.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cts.common.code.session.EncryptedSharedPrefs
import javax.inject.Inject

abstract class BaseFragment(layoutRes: Int) : Fragment(layoutRes), BaseView {

    @Inject
    lateinit var securePrefs: EncryptedSharedPrefs

    private val baseActivity: BaseActivity? by lazy {
        if (activity is BaseActivity) {
            activity as BaseActivity
        } else {
            null
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onShowLoader(show: Boolean) {
        baseActivity?.onShowLoader(show)
    }

    override fun onShowError(errorMessage: String?) {
        baseActivity?.onShowError(errorMessage)
    }

    override fun onUnAuthorized(unAuth: Boolean, errorMessage: String?) {
        baseActivity?.onUnAuthorized(unAuth, errorMessage)
    }
}