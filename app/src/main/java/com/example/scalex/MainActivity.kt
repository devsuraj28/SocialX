package com.example.scalex

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.scalex.Fragments.signInFragment
import com.example.scalex.Fragments.signUpFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class MainActivity : AppCompatActivity() {

    lateinit var frameLayout: FrameLayout
    lateinit var loginTxt: TextView
    lateinit var signupTxt: TextView

    lateinit var firebaseUser: FirebaseUser


    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        frameLayout = findViewById(R.id.main_frameLayout)
        loginTxt = findViewById(R.id.loginTxt)
        signupTxt = findViewById(R.id.signUpTxt)

        /// firebaseUser = FirebaseAuth.getInstance().currentUser!!

        setFragment(signInFragment())

        signupTxt.setOnClickListener()
        {
            setFragment(signUpFragment())
            signupTxt.setBackgroundResource(R.drawable.inactive_bg)
            loginTxt.setBackgroundResource(R.drawable.active_bg)
            signupTxt.setTextColor(ContextCompat.getColor(this, R.color.white))
            loginTxt.setTextColor(ContextCompat.getColor(this, R.color.grey))
            //logRegBtn.text = "REGISTER"
        }

        loginTxt.setOnClickListener()
        {
            setFragment(signInFragment())
            loginTxt.setBackgroundResource(R.drawable.inactive_bg)
            signupTxt.setBackgroundResource(R.drawable.active_bg)
            loginTxt.setTextColor(ContextCompat.getColor(this, R.color.white))
            signupTxt.setTextColor(ContextCompat.getColor(this, R.color.grey))
            //logRegBtn.text = "LOGIN"
        }
    }


    private fun setFragment(fragment: Fragment) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.main_frameLayout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}