package com.example.otpvarification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.otpvarification.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    private lateinit var  auth: FirebaseAuth
    private lateinit var binding:ActivityLoginBinding
    private var  currentUser: FirebaseUser?=null
    private var phoneNumber:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth=FirebaseAuth.getInstance()
        currentUser=auth.currentUser
    }
}