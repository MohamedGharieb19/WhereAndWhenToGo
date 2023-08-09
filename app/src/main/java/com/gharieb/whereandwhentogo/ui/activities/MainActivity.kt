package com.gharieb.whereandwhentogo.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gharieb.whereandwhentogo.R
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object{
        lateinit var authentication: FirebaseAuth
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        authentication = FirebaseAuth.getInstance()

    }
}