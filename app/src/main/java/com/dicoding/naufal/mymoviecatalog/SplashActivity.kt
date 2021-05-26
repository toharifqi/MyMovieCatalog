package com.dicoding.naufal.mymoviecatalog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.naufal.mymoviecatalog.databinding.ActivitySplashBinding
import com.dicoding.naufal.mymoviecatalog.ui.home.HomeActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    companion object{
        private const val DURATION = 1500L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.splashLinear.alpha = 0f
        binding.splashLinear.animate().setDuration(DURATION).alpha(1f).withEndAction {
            startActivity(Intent(this, HomeActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}