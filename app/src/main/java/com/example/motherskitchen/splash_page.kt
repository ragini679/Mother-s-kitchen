package com.example.motherskitchen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.motherskitchen.databinding.ActivitySplashPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class splash_page : AppCompatActivity() {
    private lateinit var binding:ActivitySplashPageBinding
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivitySplashPageBinding.inflate(layoutInflater)
        auth=FirebaseAuth.getInstance()
        val currentuser = auth.currentUser
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        if(currentuser!=null){
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this,home_page::class.java))
                finish()
            }, 3000)
        }
       else{
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this,login_page::class.java))
                finish()
            }, 3000)
        }
        }
    }