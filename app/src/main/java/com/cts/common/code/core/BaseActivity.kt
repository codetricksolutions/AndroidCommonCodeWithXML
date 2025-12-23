package com.cts.common.code.core

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cts.common.code.session.EncryptedSharedPrefs
import com.cts.common.code.ui.splash.SplashActivity
import com.cts.common.code.utils.DialogUtils
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), BaseView {

    @Inject
    lateinit var securePrefs: EncryptedSharedPrefs

    override fun onShowLoader(show: Boolean) {
        if (show) {
            DialogUtils.showLoading(this)
        } else {
            DialogUtils.dismissLoading()
        }
    }

    override fun onShowError(errorMessage: String?) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun onUnAuthorized(unAuth: Boolean, errorMessage: String?) {
        logout(errorMessage)
    }

    fun logout(errorMessage: String? = null) {
        errorMessage?.let {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
        securePrefs.clearAll()
        startActivity(Intent(this, SplashActivity::class.java))
        finish()
    }
}
