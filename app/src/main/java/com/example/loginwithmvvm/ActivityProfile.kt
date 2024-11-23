package com.example.loginwithmvvm

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ActivityProfile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Initialize views
        val profileImage: ImageView = findViewById(R.id.profileImage)
        val textName: TextView = findViewById(R.id.textName)
        val textFatherName: TextView = findViewById(R.id.textFatherName)
        val textCnic: TextView = findViewById(R.id.textCnic)
        val textRegNo: TextView = findViewById(R.id.textRegNo)

        // Get data from Intent
        val name = intent.getStringExtra("STUDENT_NAME")
        val fatherName = intent.getStringExtra("FATHER_NAME")
        val fatherCnic = intent.getStringExtra("FATHER_CNIC")
        val regNumber = intent.getStringExtra("REG_NUMBER")
        val imageUri = intent.getStringExtra("IMAGE_URI")

        // Set data to views
        textName.text = name
        textFatherName.text = fatherName
        textCnic.text = fatherCnic
        textRegNo.text = regNumber

        // Set profile image if needed
        if (!imageUri.isNullOrEmpty()) {
            profileImage.setImageURI(Uri.parse(imageUri))
        }
    }
}
