package com.example.loginwithmvvm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class LoginActivity : AppCompatActivity() {

    private lateinit var authViewModel: AuthViewModel
    private lateinit var loginButton: Button
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signUpLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton = findViewById(R.id.Login)
        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.Password)
        signUpLink = findViewById(R.id.sign_up_link)

        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            authViewModel.loginUser(email, password)
        }

        signUpLink.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        authViewModel.successMessage.observe(this) { success ->
            Toast.makeText(this, success, Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ActivityStudent::class.java)
            startActivity(intent)
            finish()
        }

        authViewModel.errorMessage.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }
    }
}
