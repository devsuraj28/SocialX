package com.example.scalex

import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.scalex.Fragments.signInFragment
import com.example.scalex.Fragments.signUpFragment
import com.example.scalex.Interface.NewsService
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    lateinit var frameLayout : FrameLayout

    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        frameLayout = findViewById(R.id.main_frameLayout)
        setFragment()
    }


    private fun setFragment() {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.main_frameLayout, signUpFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    //9bd814c25376407986991239a99329ff


}