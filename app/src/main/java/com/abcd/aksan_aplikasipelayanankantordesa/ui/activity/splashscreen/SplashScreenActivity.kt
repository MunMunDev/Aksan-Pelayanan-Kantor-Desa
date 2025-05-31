package com.abcd.aksan_aplikasipelayanankantordesa.ui.activity.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.abcd.aksan_aplikasipelayanankantordesa.R
import com.abcd.aksan_aplikasipelayanankantordesa.databinding.ActivitySplashScreenBinding
import com.abcd.aksan_aplikasipelayanankantordesa.ui.activity.login.LoginActivity
import com.abcd.aksan_aplikasipelayanankantordesa.ui.activity.user.main.MainActivity
import com.abcd.aksan_aplikasipelayanankantordesa.utils.SharedPreferencesLogin

@SuppressLint("FirstRun", "CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var sharedPreferencesLogin: SharedPreferencesLogin
    private val SPLASH_DELAY = 3000L // 3 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSharedPreferences()
        setupSplashScreen()
    }

    private fun setSharedPreferences() {
        sharedPreferencesLogin = SharedPreferencesLogin(this@SplashScreenActivity)
    }

    private fun setupSplashScreen() {
        // Start progress animation
        binding.progressBar.max = 100
        animateProgress()

        // Navigate to LoginActivity after delay
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToLogin()
        }, SPLASH_DELAY)
    }

    private fun animateProgress() {
        var progress = 0
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                progress += 2
                binding.progressBar.progress = progress
                if (progress < 100) {
                    handler.postDelayed(this, SPLASH_DELAY / 50) // Divide by 50 to make it smooth
                }
            }
        }
        handler.post(runnable)
    }

    private fun navigateToLogin() {
        val intent : Intent = if(sharedPreferencesLogin.getSebagai() == "user"){
            Intent(this, MainActivity::class.java)
        } else{
            Intent(this, LoginActivity::class.java)
        }
        startActivity(intent)
        finish() // Close splash screen activity
    }
}