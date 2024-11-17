package com.example.motherskitchen

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.motherskitchen.databinding.ActivitySignupPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class signup_page : AppCompatActivity() {
    private lateinit var binding: ActivitySignupPageBinding
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignupPageBinding.inflate(layoutInflater)
        auth=FirebaseAuth.getInstance()
        setContentView(binding.root)
        binding.loginText.setOnClickListener{
            startActivity(Intent(this,login_page::class.java))

        }
      binding.signinBtn.setOnClickListener{
          val emailid=binding.eidText.text.toString().trim()
          val password=binding.pwText.text.toString()
          if(password.length<6){
              Toast.makeText(this,"password must be of 6 character",Toast.LENGTH_SHORT).show()
          }
          if(emailid.isEmpty() || password.isEmpty()){
              Toast.makeText(this,"Please Fill All The Details",Toast.LENGTH_SHORT).show()
          }
          else{
              auth.createUserWithEmailAndPassword(emailid,password)
                  .addOnCompleteListener{ task->
                      if(task.isSuccessful){
                          Toast.makeText(this, "sign-up Successful", Toast.LENGTH_SHORT).show()
                          startActivity(Intent(this,home_page::class.java))
                          finish()
                      }
                      else {
                          // Sign-up failed, check for specific exceptions
                          val exception = task.exception
                          when (exception) {
                              is FirebaseAuthUserCollisionException -> {
                                  // This means the email is already in use
                                  Toast.makeText(this, "An account with this email already exists. Please log in instead.", Toast.LENGTH_LONG).show()
                              }
                              else -> {
                                  // Handle other errors
                                  Toast.makeText(this, "Sign-up failed: ${exception?.message}", Toast.LENGTH_LONG).show()
                              }
                          }
                      }
                  }
          }
      }
    }
}