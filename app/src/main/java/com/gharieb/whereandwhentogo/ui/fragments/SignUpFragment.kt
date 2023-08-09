package com.gharieb.whereandwhentogo.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gharieb.whereandwhentogo.R
import com.gharieb.whereandwhentogo.databinding.FragmentSignUpBinding
import com.gharieb.whereandwhentogo.ui.activities.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            findNavController().navigate(R.id.signInFragment)
        }

        binding.signUpButton.setOnClickListener {
            createAccount()
        }

    }

    private fun createAccount() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            MainActivity.authentication.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.e("test", "signup success ")
                    }
                }.addOnFailureListener {
                    Log.e("test", "signup success")
                }
        }
    }

}


