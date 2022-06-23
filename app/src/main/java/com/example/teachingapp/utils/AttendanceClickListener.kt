package com.example.teachingapp.utils

import android.view.View
import com.example.teachingapp.data.model.datamodel.attendance.AttendanceCourseModel

interface AttendanceClickListener {
	fun onDateClick(view: View, position: Int, attendanceCourseModel: AttendanceCourseModel)
}