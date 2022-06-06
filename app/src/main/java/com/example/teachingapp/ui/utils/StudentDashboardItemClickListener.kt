package com.example.teachingapp.ui.utils

import android.view.View
import com.example.teachingapp.ui.data.model.StudentDashBoardModel

interface StudentDashboardItemClickListener {
    fun onStudentItemClick(view: View, position: Int,studentDashBoardModel: StudentDashBoardModel)
}