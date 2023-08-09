package com.gharieb.whereandwhentogo.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gharieb.whereandwhentogo.R
import com.gharieb.whereandwhentogo.databinding.FragmentSignInBinding
import com.gharieb.whereandwhentogo.ui.activities.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 123

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUpButton.setOnClickListener {
            findNavController().navigate(R.id.signUpFragment)
        }

        binding.loginButton.setOnClickListener {
            loginAccount()
        }

        binding.googleButton.setOnClickListener {
            googleRegister()
            signInWithGoogle()
        }



    }

    private fun loginAccount(){
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()){
            MainActivity.authentication.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener{
                    if (it.isSuccessful){
                        findNavController().navigate(R.id.questionFragment)
                        Log.e("test","login success")
                    }
                }.addOnFailureListener {
                    Log.e("test","login failed")
                }
        }
    }

    private fun googleRegister() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                // Handle sign-in failure
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        MainActivity.authentication.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.questionFragment)
                    Log.e("test", "signup with google success ${MainActivity.authentication.currentUser?.email}")
                } else {
                    // Sign-in failure
                    Log.e("test", "signup with google success")
                }
            }
    }

    private fun signInWithGoogle() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


}