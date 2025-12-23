package com.cts.common.code.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.cts.common.code.R
import com.cts.common.code.core.BaseActivity
import com.cts.common.code.session.SessionKey
import com.cts.common.code.ui.auh.AuthActivity
import com.cts.common.code.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.authMain)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        gotoNextScreen()

    }

    private fun gotoNextScreen() {
        val isLoggedIn = securePrefs.getBoolean(SessionKey.LOGGED_IN)
        lifecycleScope.launch {
            delay(3000)
            if (isLoggedIn) {
                startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this@SplashActivity, AuthActivity::class.java))
                finish()
            }

        }
    }


}