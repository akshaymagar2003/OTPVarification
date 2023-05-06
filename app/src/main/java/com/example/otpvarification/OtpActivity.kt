package com.example.otpvarification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.otpvarification.databinding.ActivityLoginBinding
import com.example.otpvarification.databinding.ActivityOtpBinding
import com.google.firebase.auth.FirebaseUser

class OtpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpBinding
    private var  currentUser: FirebaseUser?=null
    private var phoneNumber:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}