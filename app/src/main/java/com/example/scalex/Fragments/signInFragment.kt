package com.example.scalex.Fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.scalex.Home
import com.example.scalex.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.ktx.Firebase


class signInFragment : Fragment() {

    lateinit var parentFrameLayout: FrameLayout
    lateinit var logEmailEdtTxt: EditText
    lateinit var logPasswordEdtTxt: EditText
    lateinit var logTxt: TextView
    lateinit var logGmailImageView: ImageView

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val Req_Code: Int = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //parentFrameLayout = requireActivity().findViewById(R.id.main_frameLayout)

        logEmailEdtTxt = view.findViewById(R.id.log_email_edt_txt)
        logPasswordEdtTxt = view.findViewById(R.id.log_password_edt_txt)
        logTxt = view.findViewById(R.id.log_txt)
        logGmailImageView = view.findViewById(R.id.log_gmail_image_view)

        firebaseAuth = FirebaseAuth.getInstance()
        FirebaseApp.initializeApp(requireContext())

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)


        //Adding TextWatcher to each EditText
        logEmailEdtTxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkFields()
            }

            override fun afterTextChanged(s: Editable?) {
                //TODO("Not yet implemented")
            }
        })
        logPasswordEdtTxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkFields()
            }

            override fun afterTextChanged(s: Editable?) {
                //TODO("Not yet implemented")
            }
        })

        logTxt.setOnClickListener()
        {
            logUser()
        }

        logGmailImageView.setOnClickListener()
        {
            Toast.makeText(requireContext(), "Logging In", Toast.LENGTH_SHORT).show()
            googleLogin()
        }

    }

    private fun googleLogin() {
        val googleSigninIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(googleSigninIntent, Req_Code)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Req_Code) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }


    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                UpdateUI(account)
            }
        } catch (e: ApiException) {
            Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun UpdateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(requireContext(), "Login Successfull", Toast.LENGTH_SHORT).show()
                startActivity(Intent(requireContext(), Home::class.java))
                requireActivity().finish()
            }
        }
    }

    private fun logUser() {
        firebaseAuth.signInWithEmailAndPassword(
            logEmailEdtTxt.text.toString(),
            logPasswordEdtTxt.text.toString()
        ).addOnCompleteListener()
        {
            if (it.isSuccessful) {
                Toast.makeText(requireContext(), "Login Successfull", Toast.LENGTH_SHORT).show()
                startActivity(Intent(requireContext(), Home::class.java))
                requireActivity().finish()
            } else {
                logEmailEdtTxt.setText("")
                logPasswordEdtTxt.setText("")
                Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun checkFields() {
        if (!TextUtils.isEmpty(logEmailEdtTxt.text.toString())) {
            if (!TextUtils.isEmpty(logPasswordEdtTxt.text.toString()) && logPasswordEdtTxt.text.length >= 8) {
                logTxt.isEnabled = true
            } else {
                logTxt.isEnabled = false
            }

        } else {
            logTxt.isEnabled = false
        }
    }

}