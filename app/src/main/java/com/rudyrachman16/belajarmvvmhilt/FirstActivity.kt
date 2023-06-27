package com.rudyrachman16.belajarmvvmhilt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.rudyrachman16.belajarmvvmhilt.ui.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FirstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var countDownStatus = true

        lifecycleScope.launch {
            delay(1000)
            countDownStatus = false
        }

        splashScreen.setKeepOnScreenCondition {
            (countDownStatus).apply {
                if (!this) {
                    startActivity(Intent(this@FirstActivity, MainActivity::class.java))
                    finish()
                }
            }
        }
    }
}