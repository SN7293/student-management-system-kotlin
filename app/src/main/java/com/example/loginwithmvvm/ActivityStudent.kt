package com.example.loginwithmvvm

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

class ActivityStudent : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var storageReference: StorageReference

    private lateinit var regNo: EditText
    private lateinit var name: EditText
    private lateinit var fatherName: EditText
    private lateinit var fatherCNIC: EditText
    private lateinit var profileImage: ImageView
    private var imageUri: Uri? = null

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false) // Enable edge-to-edge layout
        setContentView(R.layout.activity_student)

        db = FirebaseFirestore.getInstance()
        val storage = FirebaseStorage.getInstance()
        storageReference = storage.reference

        profileImage = findViewById(R.id.prfileImage)
        val registerButton: Button = findViewById(R.id.registerBtn)
        val showStudents: Button = findViewById(R.id.showStudents)
        regNo = findViewById(R.id.et_reg)
        name = findViewById(R.id.et_name)
        fatherName = findViewById(R.id.et_father_name)
        fatherCNIC = findViewById(R.id.et_father_CNIC)

        profileImage.setOnClickListener { openFileChooser() }
        registerButton.setOnClickListener { saveUserData() }
        showStudents.setOnClickListener {
            startActivity(Intent(this@ActivityStudent, AllStudent::class.java))
        }
    }

    private fun openFileChooser() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    private fun saveUserData() {
        if (imageUri != null) {
            val fileReference = storageReference.child("students/${System.currentTimeMillis()}.jpg")

            val uploadTask: UploadTask = fileReference.putFile(imageUri!!)
            uploadTask.addOnSuccessListener {
                fileReference.downloadUrl.addOnSuccessListener { uri ->
                    val regNumber = regNo.text.toString().trim()
                    val userName = name.text.toString().trim()
                    val userFatherName = fatherName.text.toString().trim()
                    val userFatherCNIC = fatherCNIC.text.toString().trim()
                    val imageUriString = uri.toString()

                    val student = ModelStudent(regNumber, userName, userFatherName, userFatherCNIC, imageUriString)

                    db.collection("Students").add(student).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this@ActivityStudent, "Data saved successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@ActivityStudent, "Failed to save data", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }.addOnFailureListener { e ->
                Toast.makeText(this@ActivityStudent, "Failed to upload image: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this@ActivityStudent, "Please select an image", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data?.data != null) {
            imageUri = data.data
            profileImage.setImageURI(imageUri)
        }
    }
}
