package com.example.otpvarification

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.otpvarification.databinding.ActivityLoginBinding
import com.example.otpvarification.databinding.ActivityOtpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class OtpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpBinding
    private var  currentUser: FirebaseUser?=null
    private var phoneNumber:String=""
    private lateinit var  auth: FirebaseAuth
    private  var authId:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth=FirebaseAuth.getInstance()
        currentUser=auth.currentUser
        authId= intent.getStringExtra("otpcr").toString()
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "signInWithCredential:success")

                    val user = task.result?.user
                    send_home()
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(ContentValues.TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }
    fun send_home(){
        val loginIntent= Intent(this@OtpActivity,MainActivity::class.java)
        startActivity(loginIntent)
        finish()
    }

    override fun onStart() {
        super.onStart()
        if(currentUser!=null){
            send_home()
            finish()
        }
    }

    fun verifyOtp(view: View) {
        val otp:String=binding.otp.text.toString()
        if(otp.isNotEmpty()){
            val credential=PhoneAuthProvider.getCredential(authId,otp)
            signInWithPhoneAuthCredential(credential)
        }
    }
}