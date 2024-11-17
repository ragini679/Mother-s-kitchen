package com.example.motherskitchen

import android.app.Activity
import android.content.Intent

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

import androidx.appcompat.app.AppCompatActivity
import com.example.motherskitchen.databinding.ActivityLoginPageBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.GoogleAuthProvider

class login_page : AppCompatActivity() {
    private lateinit var binding: ActivityLoginPageBinding
    private lateinit var auth: FirebaseAuth
//   GoogleSignInOptions is a class ,and provided by google signin api gso is a veriable of type GoogleSignInOptions
    private lateinit var gso:GoogleSignInOptions
    private lateinit var gsc:GoogleSignInClient
    var RC_SIGN_IN=20
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        binding.signupText.setOnClickListener{
            startActivity(Intent(this,signup_page::class.java))
        }
        binding.loginBtn.setOnClickListener {
            val loginemail = binding.eidText.text.toString().trim()
            val loginpassword = binding.pwText.text.toString()
            if (loginemail.isEmpty() || loginpassword.isEmpty()) {
                Toast.makeText(this, "Please Fill All The Details", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(loginemail, loginpassword)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                            val login = Intent(this, home_page::class.java)
                            startActivity(login)
                            finish()
                        } else {
                            task.exception?.let { exception ->
                                val errorManager = when (exception) {
                                    is FirebaseAuthInvalidUserException -> "No account found with this email"
                                    is FirebaseAuthInvalidCredentialsException -> "Invalid Password."
                                    else -> exception.message ?: "login Failed"
                                }
                                ShowErrorMassage(errorManager)
                            }

                        }
                    }
            }
        }
        gso=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // Add this if you need an ID token
            .requestEmail() // Add this if you want to request the user's email
            .build()
        gsc=GoogleSignIn.getClient(this,gso)
        binding.googleImg.setOnClickListener {
            val signInIntent = gsc.signInIntent
         launcher.launch(signInIntent)
        }

    }
    private val launcher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result->
        if(result.resultCode== Activity.RESULT_OK){
          val task=GoogleSignIn.getSignedInAccountFromIntent(result.data);if(task.isSuccessful){
                val account:GoogleSignInAccount?=task.result
                val credential=GoogleAuthProvider.getCredential(account?.idToken,null)
                auth.signInWithCredential(credential).addOnCompleteListener{
                    if(it.isSuccessful){
                        startActivity(Intent(this, home_page::class.java))
                        finish()

                    }
                    else{
                        Toast.makeText(this,"failed",Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else{
                Toast.makeText(this,"failed",Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(this,"failed",Toast.LENGTH_SHORT).show()
        }
    }
    fun ShowErrorMassage(message: String) {
        binding.errorMessageTextView.apply {
            text = message
            visibility = View.VISIBLE
        }
    }
}