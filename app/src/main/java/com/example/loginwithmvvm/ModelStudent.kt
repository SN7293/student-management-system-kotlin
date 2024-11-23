package com.example.loginwithmvvm


data class ModelStudent(
    var regNo: String = "",
    var name: String = "",
    var fatherName: String = "",
    var fatherCNIC: String = "",
    var imageUri: String = "" // This field stores the image URI
)
