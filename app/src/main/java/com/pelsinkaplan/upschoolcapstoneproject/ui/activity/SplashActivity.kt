package com.pelsinkaplan.upschoolcapstoneproject.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (FirebaseAuth.getInstance().currentUser == null)
            startActivity(Intent(this@SplashActivity, BeforeLoginActivity::class.java))
        else
            startActivity(Intent(this@SplashActivity, AfterLoginActivity::class.java))
    }
}