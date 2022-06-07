package com.example.teachingapp.utils

import android.view.View
import com.example.teachingapp.data.model.datamodel.studentmodel.StudentDashBoardModel

interface StudentDashboardItemClickListener {
    fun onStudentItemClick(view: View, position: Int,studentDashBoardModel: StudentDashBoardModel)
}