package com.example.loginwithmvvm

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot

class AllStudent : AppCompatActivity(), StudentAdapter.OnStudentClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var studentAdapter: StudentAdapter
    private val studentsList: MutableList<ModelStudent> = ArrayList()
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge layout
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContentView(R.layout.activity_all_students)

        recyclerView = findViewById(R.id.recycler_view_students)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize Firestore
        db = FirebaseFirestore.getInstance()
        fetchStudents() // Call to fetch students from Firestore
    }

    private fun fetchStudents() {
        // Fetch the student data from Firestore and populate the studentsList
        db.collection("Students").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (document: QueryDocumentSnapshot in task.result!!) {
                    val student = document.toObject(ModelStudent::class.java)
                    studentsList.add(student)
                }
                studentAdapter = StudentAdapter(studentsList, this, this)
                recyclerView.adapter = studentAdapter
                Toast.makeText(this, "students ${studentsList.size}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed to fetch students", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStudentClick(student: ModelStudent) {
        // Open ProfileActivity with student details
        val intent = Intent(this@AllStudent, ActivityProfile::class.java).apply {
            putExtra("STUDENT_NAME", student.name)
            putExtra("FATHER_NAME", student.fatherName)
            putExtra("FATHER_CNIC", student.fatherCNIC)
            putExtra("REG_NUMBER", student.regNo)
            putExtra("IMAGE_URI", student.imageUri)
        }
        startActivity(intent)
    }
}
