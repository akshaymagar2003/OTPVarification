package com.example.otpvarification

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.otpvarification.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

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
    fun send_home(){
        val loginIntent= Intent(this@LoginActivity,MainActivity::class.java)
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

    fun generate_OTP(view: View) {
        phoneNumber=binding.Number.text.toString()
      if(phoneNumber!=null){
          val options = PhoneAuthOptions.newBuilder(auth)
              .setPhoneNumber(phoneNumber) // Phone number to verify
              .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
              .setActivity(this) // Activity (for callback binding)
              .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
              .build()
          PhoneAuthProvider.verifyPhoneNumber(options)

      }
    }
    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Log.d(TAG, "onVerificationCompleted:$credential")
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w(TAG, "onVerificationFailed", e)

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
            }

            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            val otpIntent=Intent(this@LoginActivity,OtpActivity::class.java)
            otpIntent.putExtra("otpcr",verificationId)
            startActivity(otpIntent)
            finish()

            Log.d(TAG, "onCodeSent:$verificationId")

            // Save verification ID and resending token so we can use them later
//            storedVerificationId = verificationId
//            resendToken = token
        }
    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")

                    val user = task.result?.user
                    send_home()
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }
}