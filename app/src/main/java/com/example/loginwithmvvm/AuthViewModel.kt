package com.example.loginwithmvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {

    private val authRepository = AuthRepository()

    private val _successMessage = MutableLiveData<String>()
    val successMessage: LiveData<String> get() = _successMessage

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun loginUser(email: String, password: String) {
        authRepository.authenticateUser(email, password, object : AuthRepository.OnFirestoreOperationComplete {
            override fun onSuccess(message: String) {
                _successMessage.postValue(message)
            }

            override fun onError(error: String) {
                _errorMessage.postValue(error)
            }
        })
    }

    fun signupUser(email: String, password: String, confirmPassword: String) {
        if (password != confirmPassword) {
            _errorMessage.value = "Passwords do not match!"
            return
        }

        val user = Modeluser(email, password, confirmPassword)
        authRepository.signupUser(user, object : AuthRepository.OnFirestoreOperationComplete {
            override fun onSuccess(message: String) {
                _successMessage.postValue(message)
            }

            override fun onError(error: String) {
                _errorMessage.postValue(error)
            }
        })
    }
}
