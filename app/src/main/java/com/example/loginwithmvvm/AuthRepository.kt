package com.example.loginwithmvvm

import com.google.firebase.firestore.FirebaseFirestore

class AuthRepository {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    // Sign-up user
    fun signupUser(user: Modeluser, callback: OnFirestoreOperationComplete) {
        db.collection("User")
            .add(user)
            .addOnSuccessListener {
                callback.onSuccess("Sign-up successful!")
            }
            .addOnFailureListener { e ->
                callback.onError(e.message ?: "Sign-up failed!")
            }
    }

    fun authenticateUser(email: String, password: String, callback: OnFirestoreOperationComplete) {
        db.collection("User")
            .whereEqualTo("email", email)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result
                    if (result != null && !result.isEmpty) { // Use !result.isEmpty for QuerySnapshot
                        for (document in result) {
                            val user = document.toObject(Modeluser::class.java)
                            if (user.password == password) {
                                callback.onSuccess("Login successful!")
                                return@addOnCompleteListener
                            }
                        }
                        callback.onError("Incorrect password!")
                    } else {
                        callback.onError("User not found!")
                    }
                } else {
                    callback.onError("Failed to fetch user!")
                }
            }
            .addOnFailureListener { e ->
                callback.onError(e.message ?: "Authentication failed!")
            }
    }

    interface OnFirestoreOperationComplete {
        fun onSuccess(message: String)
        fun onError(error: String)
    }
}
