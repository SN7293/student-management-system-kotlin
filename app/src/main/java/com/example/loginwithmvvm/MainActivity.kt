package com.example.loginwithmvvm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider


class MainActivity : AppCompatActivity() {

    private lateinit var authViewModel: AuthViewModel

        private lateinit var Singup: Button
        private lateinit var email: EditText
        private lateinit var Password: EditText
        private lateinit var CONFIRMPASSWORD: EditText
        private lateinit var signInlink: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        // Initialize the views using findViewById
            Singup = findViewById(R.id.SignUp)
            email = findViewById(R.id.email)
            Password = findViewById(R.id.Password)
            CONFIRMPASSWORD = findViewById(R.id.CONFIRMPASSWORD)
            signInlink = findViewById(R.id.sign_in_link)

            // Initialize the ViewModel
            authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)




            Singup.setOnClickListener {
            val email = email.text.toString().trim()
            val password = Password.text.toString().trim()
            val confirmPassword = CONFIRMPASSWORD.text.toString().trim()
            authViewModel.signupUser(email, password, confirmPassword)
        }

        signInlink.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        authViewModel.successMessage.observe(this) { success ->
            Toast.makeText(this, success, Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        authViewModel.errorMessage.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }
    }
}
