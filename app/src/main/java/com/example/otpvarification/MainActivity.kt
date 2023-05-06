package com.example.otpvarification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.otpvarification.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
   private lateinit var binding:ActivityMainBinding
   private lateinit var  auth:FirebaseAuth
   private var  currentUser:FirebaseUser?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        auth=FirebaseAuth.getInstance()
        currentUser=auth.currentUser
    }
    fun logout(view: View){
        auth.signOut()
        login()
    }
    fun login(){
        val loginIntent= Intent(this@MainActivity,LoginActivity::class.java)
       startActivity(loginIntent)
        finish()
    }

    override fun onStart() {
        super.onStart()
       if(currentUser==null){
           login()
       }
        else{
            var userInfo=currentUser!!.phoneNumber
           binding.status.text="Welcome $userInfo"

        }

    }

}