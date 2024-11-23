package com.example.loginwithmvvm

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private val studentList: List<ModelStudent>,
    private val context: Context,
    private val onStudentClickListener: OnStudentClickListener
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    interface OnStudentClickListener {
        fun onStudentClick(student: ModelStudent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = studentList[position]
        holder.nameTextView.text = student.name
        holder.regNoTextView.text = student.regNo

        holder.itemView.setOnClickListener {
            onStudentClickListener.onStudentClick(student)
        }
    }

    override fun getItemCount(): Int = studentList.size

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.tv_name)
        val regNoTextView: TextView = itemView.findViewById(R.id.tv_reg_no)
        val tvFatherName: TextView = itemView.findViewById(R.id.tv_father_name)
    }
}
