package com.example.scalex.Fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.scalex.Home
import com.example.scalex.R
import com.google.firebase.auth.FirebaseAuth

class signUpFragment : Fragment() {

    lateinit var regNameEdtTxt: EditText
    lateinit var regEmailEdtTxt: EditText
    lateinit var regNumberEdtTxt: EditText
    lateinit var regPasswordEdtTxt: EditText
    lateinit var regTermsCheckBox: CheckBox
    lateinit var regTxt: TextView

    lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        regNameEdtTxt = view.findViewById(R.id.reg_name_edt_txt)
        regEmailEdtTxt = view.findViewById(R.id.reg_email_edt_txt)
        regNumberEdtTxt = view.findViewById(R.id.reg_number_edt_txt)
        regPasswordEdtTxt = view.findViewById(R.id.reg_password_edt_txt)
        regTermsCheckBox = view.findViewById(R.id.reg_terms_checkBox)
        regTxt = view.findViewById(R.id.reg_txt)

        firebaseAuth = FirebaseAuth.getInstance()

        //Adding TextWatchers to each EditText's
        regNameEdtTxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkFieldInputs()
            }

            override fun afterTextChanged(s: Editable?) {
                //TODO("Not yet implemented")
            }

        })
        regEmailEdtTxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkFieldInputs()
            }

            override fun afterTextChanged(s: Editable?) {
                //TODO("Not yet implemented")
            }

        })
        regNumberEdtTxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkFieldInputs()
            }

            override fun afterTextChanged(s: Editable?) {
                //TODO("Not yet implemented")
            }

        })
        regPasswordEdtTxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkFieldInputs()
            }

            override fun afterTextChanged(s: Editable?) {
                //TODO("Not yet implemented")
            }

        })

        regTxt.setOnClickListener()
        {
            createFBUser()
        }

    }

    private fun createFBUser() {
        firebaseAuth.createUserWithEmailAndPassword(
            regEmailEdtTxt.text.toString(),
            regPasswordEdtTxt.text.toString()
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(requireContext(), "Regsiteration Successfull", Toast.LENGTH_SHORT)
                    .show()
                startActivity(Intent(requireContext(), Home::class.java))
                requireActivity().finish()
            } else {
                Toast.makeText(requireContext(), "Regsiteration Failed", Toast.LENGTH_SHORT).show()
                startActivity(Intent(requireContext(), Home::class.java))
                requireActivity().finish()
            }
        }
    }


    private fun checkFieldInputs() {
        if (!TextUtils.isEmpty(regNameEdtTxt.text.toString()) && regNameEdtTxt.length() <= 15) {
            if (!TextUtils.isEmpty(regEmailEdtTxt.text.toString())) {
                if (!TextUtils.isEmpty(regNumberEdtTxt.text.toString()) && regNumberEdtTxt.length() == 10) {
                    if (!TextUtils.isEmpty(regPasswordEdtTxt.text.toString()) && regPasswordEdtTxt.length() >= 8) {


                        regTermsCheckBox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
                            if (compoundButton.isChecked) {
                                regTxt.setEnabled(true)
                            } else {
                                regTxt.setEnabled(false)
                                Toast.makeText(
                                    requireContext(),
                                    "Please Agree to our Terms & Conditions!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
                    } else {
                        regTxt.isEnabled = false
                    }
                } else {
                    regTxt.isEnabled = false
                }
            } else {
                regTxt.isEnabled = false
            }
        } else {
            regTxt.isEnabled = false
        }
    }


}